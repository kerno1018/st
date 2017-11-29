package sell;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Stock;
import com.stock.util.DB;
import com.stock.util.Util;

import java.util.Date;

/**
 * Created by kerno on 1/15/2016.
 */
public class ConcurrentCommonCommand extends Command {

    public ConcurrentCommonCommand(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        super(advise, ownFund, account, sellCount);
    }

    protected Double getSellCount(){
        Double sellMoney = (sellA.getBuyTwoPrice() + sellB.getBuyTwoPrice()) * sellCount ;
        Double split = sellMoney/(advise.getMarketA().getSellTwoPrice() + advise.getMarketB().getSellTwoPrice());
        return split;
    }

    @Override
    public void run() {
        Stock adviseA = advise.getMarketA();
        Stock adviseB = advise.getMarketB();

        double split = getSellCount();
        int count = (int)(split/100)*100;

        logger.warn("sell/buy process start");
        logger.warn("sell/buy:"+sellCount);
        info.setSellTime(new Date());
        sellB();
        buy(adviseB,count);
        sellA();
        buy(adviseA,count);
        info.setBuyTime(new Date());
        info.setTheoryBuyCount((split*2)+"");
        // loop and check order until it successful
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.addVersion();

        if(!checkSuccessOrNot(sellOrder,500,account,false,sellCount.intValue(),sellCount.intValue())){
            account.subtractVersion();
            account.syncMoney();
            logger.warn("sell process end with error");
            return;
        }
        // sync can use money after sell success
        account.syncMoney();
        DealLogBuilder.buildBuyInfo(info,account,advise);
        if(!checkSuccessOrNot(buyOrder,1000,account,true,count,count)){
            account.subtractVersion();
            logger.warn("buy process end with error ");
            return;
        }
        logger.warn("sell/buy process end");

        info.setCreateDate(new Date());
        DB.dealQueue.add(info);
        account.subtractVersion();
    }


}
