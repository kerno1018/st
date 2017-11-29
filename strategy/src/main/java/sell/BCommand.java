package sell;


import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Stock;
import com.stock.util.DB;

import java.util.Date;

/**
 * Created by kerno on 1/15/2016.
 */
public class BCommand extends Command {

    public BCommand(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        super(advise, ownFund, account, sellCount);
    }

    protected Double getSellCount(){
        Double sellMoney = (sellB.getBuyTwoPrice()) * sellCount;
        Double split = sellMoney/(advise.getMarketB().getSellTwoPrice());
        return split;
    }

    @Override
    public void run() {
        logger.warn("sell process start");
        logger.warn("sell:"+sellCount);
        Double split = getSellCount();
        int count = (int) (split / 100) * 100;
        info.setSellTime(new Date());
        Stock adviseB = advise.getMarketB();
        sellB();
        buy(adviseB,count);
        info.setBuyTime(new Date());
        info.setTheoryBuyCount((split)+"");

        // loop and check order until it successful
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.addVersion();
        if(!checkBSuccessOrNot(sellOrder,500,account,false,sellCount.intValue())){
            account.subtractVersion();
            account.syncMoney();
            logger.warn("sell process end with error");
            return;
        }
        account.syncMoney();
        DealLogBuilder.buildBuyInfo(info,account,advise);
        if(!checkBSuccessOrNot(buyOrder,1000,account,true,count)){
            account.subtractVersion();
            logger.warn("buy process end with error ");
            return;
        }
        info.setCreateDate(new Date());
        logger.warn("buy process end");
        DB.dealQueue.add(info);
        account.subtractVersion();
    }


}
