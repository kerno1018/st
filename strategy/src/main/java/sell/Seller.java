package sell;

import com.stock.bean.*;
import com.stock.entity.LogInfo;
import moke.Moker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stock.util.DB;
import com.stock.util.Util;


import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kerno on 1/15/2016.
 */
public class Seller implements Runnable{
    Logger logger = LoggerFactory.getLogger(Seller.class);
    private Account account;
    private BaseFund advise;
    private BaseFund ownFund;
    private Double totalMoney = 0.0;
    private LogInfo info;
    private Double sellCount;

    private volatile List<String> sellOrder = new ArrayList<>();
    private volatile List<String> buyOrder = new ArrayList<>();

    public Seller(BaseFund advise, BaseFund ownFund, Account account,Double sellCount) {
        this.advise = advise;
        this.ownFund = ownFund;
        this.account = account;
        this.sellCount = sellCount;
    }

    @Override
    public void run() {
        OwnStock ownB  = account.getOwnStock().get(ownFund.getFundB_id());
        OwnStock ownA  = account.getOwnStock().get(ownFund.getFundA_id());

        if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B){
            if(ownB!= null && ownB.getCanUseCount() == 0.0){
                logger.info("can use account is zero will skip sell and buy.");
                return;
            }
        }else{
            if(ownA != null && ownA.getCanUseCount() == 0.0 || ownB!= null && ownB.getCanUseCount() == 0.0){
                logger.info("can use account is zero will skip sell and buy.");
                return;
            }
        }

        if(sellCount == null || sellCount ==0.0){
            logger.info("sell count is null will return...");
            return;
        }
        if(Keys.STRATEGY_TYPE != StrategyType.STRATEGY_B){
            ownA.setCanUseCount(ownA.getCanUseCount() - sellCount);
        }

        ownB.setCanUseCount(ownB.getCanUseCount() - sellCount);

        Stock sellA = ownFund.getMarketA();
        Stock sellB = ownFund.getMarketB();
        boolean checkA = Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B;
        boolean checkB = false;
        info = new LogInfo();
        logger.info("sell process start");
        logger.info("sell:"+sellCount);
        new Thread(new sell(sellB,ownB,sellCount)).start();
        if(Keys.STRATEGY_TYPE != StrategyType.STRATEGY_B){
            new Thread(new sell(sellA,ownA,sellCount)).start();
        }
        info.setSellTime(new Date());
        // loop and check order until it successful
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.addVersion();
//        SellUtil.buildSellInfo(info,account,ownA,ownFund,ownB,sellA,sellB);

        if(!checkSuccessOrNot(checkA,checkB,sellOrder,500,account,false,sellCount.intValue(),sellCount.intValue())){
            logger.info("sell process end with error");
            return;
        }
        logger.info("sell process end");

        Stock adviseA = advise.getMarketA();
        Stock adviseB = advise.getMarketB();
        if(totalMoney > 0.0){
            totalMoney += account.getFreeMoney();
            Double sellMoney = totalMoney;
            Double split = totalMoney/(adviseA.getSellTwoPrice() + adviseB.getSellTwoPrice());
            if(StrategyType.STRATEGY_B == Keys.STRATEGY_TYPE){
                split = totalMoney / adviseB.getSellTwoPrice();
            }
            int count = (int)(split/100)*100;
            logger.info("buy process start");
            new Thread(new buy(adviseB,count)).start();
            if(StrategyType.STRATEGY_B != Keys.STRATEGY_TYPE) {
                new Thread(new buy(adviseA, count)).start();
            }
            info.setBuyTime(new Date());
            info.setTheoryBuyCount((split*2)+"");
            if(StrategyType.STRATEGY_B == Keys.STRATEGY_TYPE) {
                info.setTheoryBuyCount((split)+"");
            }
            DealLogBuilder.buildBuyInfo(info,account,advise);

            if(!checkSuccessOrNot(false,false,buyOrder,3000,account,true,count,count)){
                logger.info("buy process end with error ");
                return;
            }
            logger.info("buy process end");
            info.setFreeMoney(sellMoney - totalMoney);
            account.addFreeMoney(info.getFreeMoney());
            info.setCreateDate(new Date());
            DB.dealQueue.add(info);
            account.subtractVersion();
        }
    }

    public Boolean checkSuccessOrNot(Boolean checkA, Boolean checkB, List<String> orderNos, Integer second, Account account,Boolean buy,int checkCountA,int checkCountB){
        boolean condition = checkA&checkB;
        // check the deal order had include own stock or not.
        int loopLimit = 600;
        int count = 0;
        while(!condition){
            totalMoney = 0.0;
            Double dealCount = 0.0;
            if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B){
                Keys.CHECK_ORDER_NUM = 1;
            }else{
                Keys.CHECK_ORDER_NUM = 2;
            }
            if(orderNos.size() == Keys.CHECK_ORDER_NUM ){
                List<OrderInfo> dealOrders = null;
                if(Keys.DEBUG){
                    info = new LogInfo();
                    orderNos.set(0,"LF123456");
                    orderNos.set(1,"LF654321");
                    dealOrders = Moker.getOrderInfo();
                }else{
//                    dealOrders = Util.getTurnOverInfo(account.getClient(),"",null);
                    dealOrders = account.getDealOrderSuccess();
                }
                if(dealOrders.size() > 0){
                    boolean checkContinue = true;
                    Double dealVolumeA = 0.0;
                    Double dealVolumeB=0.0;
                    Double averagePriceA=0.0;
                    Double averagePriceB=0.0;
                    for(OrderInfo deal : dealOrders) {
                        if(Keys.ERROR_ORDER_NO.equals(deal.getOrderNo())){
                            return false;
                        }
                        if(orderNos.contains(deal.getOrderNo())){
                            if(buy){
                                if(Keys.STRATEGY_TYPE != StrategyType.STRATEGY_B){
                                    if(deal.getCode().equals(advise.getFundA_id())){
                                        dealVolumeA += deal.getCount();
                                        dealCount += deal.getCount();
                                        averagePriceA += deal.getCount()/checkCountA * deal.getPrice();
                                        totalMoney += deal.getMoney();
                                        info.setBuyPriceA(averagePriceA);
                                    }
                                }
                                if(deal.getCode().equals(advise.getFundB_id())){
                                    dealVolumeB+=deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePriceB += deal.getCount()/checkCountB * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setBuyPriceB(averagePriceB);
                                }
                            }else{
                                if(Keys.STRATEGY_TYPE != StrategyType.STRATEGY_B) {
                                    if (deal.getCode().equals(ownFund.getFundA_id())) {
                                        dealVolumeA += deal.getCount();
                                        dealCount += deal.getCount();
                                        averagePriceA += deal.getCount() / checkCountA * deal.getPrice();
                                        totalMoney += deal.getMoney();
                                        info.setSellPriceA(averagePriceA);
                                    }
                                }
                                if(deal.getCode().equals(ownFund.getFundB_id())){
                                    dealVolumeB+=deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePriceB += deal.getCount()/checkCountB * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setSellPriceB(averagePriceB);
                                }
                            }
                        }
                    }
                    checkContinue = dealVolumeA.intValue() == checkCountA && dealVolumeB.intValue() == checkCountB;
                    if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B){
                        checkContinue = dealVolumeB.intValue() == checkCountB;
                    }
                    // if type is not Keys.DEAL_TYPE will skip below process
                    if(checkContinue){
                        checkA = true;
                        checkB = true;
                    }
                }
            }
            condition = checkA&checkB;
            if(!condition){
                try {
                    Thread.sleep(second);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                if(Keys.DEBUG){
                    break;
                }
                if (buy) {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B){
                        Double ss = Double.valueOf(df.format(((advise.getMarketA().getBuyTwoPrice() + info.getBuyPriceB()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setDealBuyPreminum(ss);
                        info.setRealBuyCount((dealCount/2)+"");
                    }else{
                        Double ss = Double.valueOf(df.format(((info.getBuyPriceA() + info.getBuyPriceB()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setDealBuyPreminum(ss);
                        info.setRealBuyCount((dealCount/2)+"");
                    }
//                    info.setTheoryCountMoney(Double.valueOf(info.getTheoryBuyCount()) / 2 * info.getBuyPriceA() + Double.valueOf(info.getTheoryBuyCount()) / 2 * info.getBuyPriceB());
//                    info.setCountMoney(totalMoney);
                } else {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B){
                        Double ss = Double.valueOf(df.format(((ownFund.getMarketA().getSellTwoPrice() + info.getSellPriceB()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setDealSellPreminum(ss);
                        info.setRealSellCount((dealCount/2)+"");
                    }else{
                        Double ss = Double.valueOf(df.format(((info.getSellPriceA() + info.getSellPriceB()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setDealSellPreminum(ss);
                        info.setRealSellCount((dealCount/2)+"");
                    }

                }
            }
            count++;
            if(count > loopLimit){
                logger.info(account.getId() + (second==500?" 卖出":"买入") + "----订单未成交");
            }
        }
        logger.info(account.getId() + " -- check done"+ totalMoney);
        return condition;
    }

    class buy implements Runnable{
        private Stock buy;
        private Integer count;

        public buy(Stock buy, Integer count){
            this.buy = buy;
            this.count = count;
        }
        @Override
        public void run() {
            String orderNo = "";
            try {
                if(Keys.DEBUG){
                    logger.info("buy");
                }
                orderNo = Util.buy(account.getClient(),account.getId(),buy.getId(),buy.getSellTwoPrice(),count);
                buyOrder.add(orderNo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class sell implements Runnable{
        private Stock sell;
        private OwnStock own;
        private Double canSellCount;

        public sell(Stock sell, OwnStock own,Double canSellCount){
            this.sell = sell;
            this.own = own;
            this.canSellCount = canSellCount;
        }
        @Override
        public void run() {
            String orderNo = "";
            try {
                if(Keys.DEBUG){
                    logger.info("sell");
                }
                orderNo = Util.sell(account.getClient(),account.getId(),sell.getBuyTwoPrice(),own.getCostPrice(),own.getId(),canSellCount.intValue());
                sellOrder.add(orderNo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BaseFund getAdvise() {
        return advise;
    }

    public void setAdvise(BaseFund advise) {
        this.advise = advise;
    }

    public BaseFund getOwnFund() {
        return ownFund;
    }

    public void setOwnFund(BaseFund ownFund) {
        this.ownFund = ownFund;
    }
}
