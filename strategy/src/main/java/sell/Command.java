package sell;

import com.stock.bean.*;
import com.stock.entity.LogInfo;
import com.stock.util.JudgeUtil;
import moke.Moker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stock.util.Util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 2016/4/5.
 */
public abstract class Command implements Runnable{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Account account;
    protected BaseFund advise;
    protected BaseFund ownFund;
    protected Double totalMoney = 0.0;
    protected LogInfo info = new LogInfo();
    protected Double sellCount;
    protected OwnStock ownA;
    protected OwnStock ownB;
    protected Stock sellA;
    protected Stock sellB;

    protected volatile List<String> sellOrder = new ArrayList<>();
    protected volatile List<String> buyOrder = new ArrayList<>();

    public Command(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        this.advise = advise;
        this.ownFund = ownFund;
        this.account = account;
        this.sellCount = sellCount;
        ownB  = account.getOwnStock().get(ownFund.getFundB_id());
        ownA  = account.getOwnStock().get(ownFund.getFundA_id());
        sellA = ownFund.getMarketA();
        sellB = ownFund.getMarketB();
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
                    logger.warn("buy");
                    buyOrder.add("LF654321");
                    return;
                }
                if(JudgeUtil.isEagerRolling()){
                    orderNo = Util.buy(account.getClient(),account.getId(),buy.getId(),buy.getBuyOnePrice(),count);
                }else{
                    orderNo = Util.buy(account.getClient(),account.getId(),buy.getId(),buy.getSellTwoPrice(),count);
                }
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
                    logger.warn("sell");
                    sellOrder.add("LF123456");
                    return;
                }
                if(JudgeUtil.isEagerRolling()){
                    orderNo = Util.sell(account.getClient(),account.getId(),sell.getSellOnePrice(),own.getCostPrice(),own.getId(),canSellCount.intValue());
                }else{
                    orderNo = Util.sell(account.getClient(),account.getId(),sell.getBuyTwoPrice(),own.getCostPrice(),own.getId(),canSellCount.intValue());
                }
                sellOrder.add(orderNo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sellB(){
        new Thread(new sell(sellB,ownB,sellCount)).start();
        DealLogBuilder.buildSellBInfo(info,account,ownB,ownFund,sellB);
    }

    public void sellA(){
        new Thread(new sell(sellA,ownA,sellCount)).start();
        DealLogBuilder.buildSellAInfo(info, account, ownA, ownFund, sellA);
    }

    public void buy(Stock advise,Integer count){
        new Thread(new buy(advise,count)).start();
    }

    public Boolean checkSuccessOrNot( List<String> orderNos, Integer second, Account account,Boolean buy,int checkCountA,int checkCountB){
        boolean checkA=false;
        boolean checkB=false;
        boolean condition = checkA&checkB;
        // check the deal order had include own stock or not.
        int loopLimit = 600;
        int count = 0;
        while(!condition){
            if(Keys.FORCE_STOP){
                break;
            }
            totalMoney = 0.0;
            Double dealCount = 0.0;
            if(orderNos.size() == 2 ){
            if(orderNos.contains(Keys.ERROR_ORDER_NO)){
                return false;
            }
                List<OrderInfo> dealOrders = null;
                dealOrders = account.getDealOrderSuccess();
                if(dealOrders.size() > 0){
                    boolean checkContinue = true;
                    Double dealVolumeA = 0.0;
                    Double dealVolumeB=0.0;
                    Double averagePriceA=0.0;
                    Double averagePriceB=0.0;
                    for(OrderInfo deal : dealOrders) {
                        if(orderNos.contains(deal.getOrderNo())){
                            if(buy){
                                if(deal.getCode().equals(advise.getFundA_id())){
                                    dealVolumeA += deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePriceA += deal.getCount()/checkCountA * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setBuyPriceA(averagePriceA);
                                }
                                if(deal.getCode().equals(advise.getFundB_id())){
                                    dealVolumeB+=deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePriceB += deal.getCount()/checkCountB * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setBuyPriceB(averagePriceB);
                                }
                            }else{
                                if (deal.getCode().equals(ownFund.getFundA_id())) {
                                    dealVolumeA += deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePriceA += deal.getCount() / checkCountA * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setSellPriceA(averagePriceA);
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
                    logger.warn(account.getId() + (buy?" buy":" sell") +" dealVolumeA is " + dealVolumeA.intValue());
                    logger.warn(account.getId() + (buy?" buy":" sell") +" dealVolumeB is " + dealVolumeB.intValue());
                    logger.warn(account.getId() + (buy?" buy":" sell") +" checkCountA is " + checkCountA);
                    logger.warn(account.getId() + (buy?" buy":" sell") +" checkCountB is " + checkCountB);
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
                    Double ss = Double.valueOf(df.format(((info.getBuyPriceA() + info.getBuyPriceB()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                    info.setDealBuyPreminum(ss);
                    info.setRealBuyCount((dealCount/2)+"");
                } else {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    Double ss = Double.valueOf(df.format(((info.getSellPriceA() + info.getSellPriceB()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                    info.setDealSellPreminum(ss);
                    info.setRealSellCount((dealCount/2)+"");
                }
            }
            count++;
            if(count > loopLimit){
                logger.warn(account.getId() + (second==500?" 卖出":"买入") + "----订单未成交");
            }
            if(orderNos.size()>0){
                logger.warn(account.getId() + (buy?" buy":" sell") + " checking "+orderNos.size()+" order : " + orderNos.get(0));
            }
        }
        logger.warn(account.getId() + " -- check done"+ totalMoney);
        return condition;
    }

    public Boolean checkBSuccessOrNot( List<String> orderNos, Integer second, Account account,Boolean buy,int checkCount){
        boolean checkB=false;
        boolean condition = checkB;
        // check the deal order had include own stock or not.
        int loopLimit = 600;
        int count = 0;
        while(!condition){
            if(Keys.FORCE_STOP){
                break;
            }
            totalMoney = 0.0;
            Double dealCount = 0.0;
            if(orderNos.size() == 1 ){
            if(orderNos.contains(Keys.ERROR_ORDER_NO)){
                return false;
            }
                List<OrderInfo> dealOrders = null;
                if(Keys.DEBUG){
                    dealOrders = Moker.getOrderInfo();
                }else{
                    dealOrders = account.getDealOrderSuccess();
                }
                if(dealOrders.size() > 0){
                    boolean checkContinue = true;
                    Double dealVolume = 0.0;
                    Double averagePrice=0.0;
                    for(OrderInfo deal : dealOrders) {
                        if(orderNos.contains(deal.getOrderNo())){
                            if(buy){
                                if(deal.getCode().equals(advise.getFundB_id())){
                                    dealVolume+=deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePrice += deal.getCount()/checkCount * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setBuyPriceB(averagePrice);
                                }
                            }else{
                                if(deal.getCode().equals(ownFund.getFundB_id())){
                                    dealVolume+=deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePrice += deal.getCount()/checkCount * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setSellPriceB(averagePrice);
                                }
                            }
                        }
                    }
                    checkContinue = dealVolume.intValue() >= checkCount;
                    logger.warn(account.getId() + (buy?" buy":" sell") +" dealVolume is " + dealVolume.intValue());
                    logger.warn(account.getId() + (buy?" buy":" sell") +" checkCount is " + checkCount);
                    // if type is not Keys.DEAL_TYPE will skip below process
                    if(checkContinue){
                        checkB = true;
                    }
                }
            }
            condition = checkB;
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
                    Double ss = 0.0;
                    if(JudgeUtil.isEagerRolling()){
                        ss = Double.valueOf(df.format(((advise.getMarketA().getBuyOnePrice() + info.getBuyPriceB()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setBuyPriceA(advise.getMarketA().getBuyOnePrice());
                    }else{
                        ss = Double.valueOf(df.format(((advise.getMarketA().getSellOnePrice() + info.getBuyPriceB()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setBuyPriceA(advise.getMarketA().getSellOnePrice());
                    }
                    info.setDealBuyPreminum(ss);
                    info.setRealBuyCount((dealCount/2)+"");
                } else {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    Double ss = 0.0;
                    if(JudgeUtil.isEagerRolling()){
                        ss = Double.valueOf(df.format(((ownFund.getMarketA().getSellOnePrice() + info.getSellPriceB()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setSellPriceA(ownFund.getMarketA().getSellOnePrice());
                    } else{
                        ss = Double.valueOf(df.format(((ownFund.getMarketA().getBuyOnePrice() + info.getSellPriceB()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setSellPriceA(ownFund.getMarketA().getBuyOnePrice());
                    }
                    info.setDealSellPreminum(ss);
                    info.setRealSellCount((dealCount/2)+"");
                }
            }
            count++;
            if(count > loopLimit){
                logger.warn(account.getId() + (second==500?" 卖出":"买入") + "----订单未成交");
            }
            if(orderNos.size()>0){
                logger.warn(account.getId()+ (buy?" buy":" sell") + " checking "+orderNos.size()+" order : " + orderNos.get(0));
            }
        }
        logger.warn(account.getId() + (buy?" buy":" sell") + " -- check done"+ totalMoney);
        return condition;
    }

    public Boolean checkASuccessOrNot( List<String> orderNos, Integer second, Account account,Boolean buy,int checkCount){
        boolean checkA=false;
        boolean condition = checkA;
        // check the deal order had include own stock or not.
        int loopLimit = 600;
        int count = 0;
        while(!condition){
            if(Keys.FORCE_STOP){
                break;
            }
            totalMoney = 0.0;
            Double dealCount = 0.0;
            if(orderNos.size() == 1 ){
                if(orderNos.contains(Keys.ERROR_ORDER_NO)){
                    return false;
                }
                List<OrderInfo> dealOrders = null;
                dealOrders = account.getDealOrderSuccess();
                if(dealOrders.size() > 0){
                    boolean checkContinue = true;
                    Double dealVolume = 0.0;
                    Double averagePrice=0.0;
                    for(OrderInfo deal : dealOrders) {
                        if(orderNos.contains(deal.getOrderNo())){
                            if(buy){
                                if(deal.getCode().equals(advise.getFundA_id())){
                                    dealVolume += deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePrice += deal.getCount()/checkCount * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setBuyPriceA(averagePrice);
                                }
                            }else{
                                if (deal.getCode().equals(ownFund.getFundA_id())) {
                                    dealVolume += deal.getCount();
                                    dealCount += deal.getCount();
                                    averagePrice += deal.getCount() / checkCount * deal.getPrice();
                                    totalMoney += deal.getMoney();
                                    info.setSellPriceA(averagePrice);
                                }
                            }
                        }
                    }
                    checkContinue = dealVolume.intValue() == checkCount;
                    logger.warn(account.getId() + (buy?" buy":" sell") +" dealVolume is " + dealVolume.intValue());
                    logger.warn(account.getId() + (buy?" buy":" sell") +" checkCount is " + checkCount);
                    // if type is not Keys.DEAL_TYPE will skip below process
                    if(checkContinue){
                        checkA = true;
                    }
                }
            }
            condition = checkA;
            if(!condition){
                try {
                    Thread.sleep(second);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                if (buy) {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    Double ss = 0.0;
                    if(JudgeUtil.isEagerRolling()){
                        ss = Double.valueOf(df.format(((advise.getMarketB().getBuyOnePrice() + info.getBuyPriceA()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setBuyPriceB(advise.getMarketB().getBuyOnePrice());
                    }else{
                        ss = Double.valueOf(df.format(((advise.getMarketB().getSellOnePrice() + info.getBuyPriceA()) / 2 - info.getBuyFundNetValue()) / info.getBuyFundNetValue())) * 100;
                        info.setBuyPriceB(advise.getMarketB().getSellOnePrice());
                    }
                    info.setDealBuyPreminum(ss);
                    info.setRealBuyCount((dealCount/2)+"");
                } else {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    Double ss = 0.0;
                    if(JudgeUtil.isEagerRolling()){
                        ss = Double.valueOf(df.format(((ownFund.getMarketB().getSellOnePrice() + info.getSellPriceA()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setSellPriceB(ownFund.getMarketB().getSellOnePrice());
                    } else{
                        ss = Double.valueOf(df.format(((ownFund.getMarketB().getBuyOnePrice() + info.getSellPriceA()) / 2 - info.getSellFundNetValue()) / info.getSellFundNetValue())) * 100;
                        info.setSellPriceB(ownFund.getMarketB().getBuyOnePrice());
                    }
                    info.setDealSellPreminum(ss);
                    info.setRealSellCount((dealCount/2)+"");
                }
            }
            count++;
            if(count > loopLimit){
                logger.warn(account.getId() + (second==500?" 卖出":"买入") + "----订单未成交");
            }
            if(orderNos.size()>0){
                logger.warn(account.getId() + (buy?" buy":" sell") + " checking "+orderNos.size()+" order : " + orderNos.get(0));
            }
        }
        logger.warn(account.getId() + (buy?" buy":" sell") + " -- check done"+ totalMoney);
        return condition;
    }
}
