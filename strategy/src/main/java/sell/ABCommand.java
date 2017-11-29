package sell;

import com.stock.bean.*;
import com.stock.util.DB;

import java.util.Date;

/**
 * Created by kerno on 1/15/2016.
 */
public class ABCommand extends Command {

    public ABCommand(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        super(advise, ownFund, account, sellCount);
    }

    @Override
    public void run() {
        logger.warn("sell process start");
        logger.warn("sell:"+sellCount);
        info.setSellTime(new Date());
        sellB();
        sellA();

        // loop and check order until it successful
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.addVersion();

        if(!checkSuccessOrNot(sellOrder,500,account,false,sellCount.intValue(),sellCount.intValue())){
            logger.warn("sell process end with error");
            return;
        }
        logger.warn("sell process end");

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
            logger.warn("buy process start");
            buy(adviseB,count);
            buy(adviseA,count);
            info.setBuyTime(new Date());
            info.setTheoryBuyCount((split*2)+"");
            DealLogBuilder.buildBuyInfo(info,account,advise);
            if(!checkSuccessOrNot(buyOrder,3000,account,true,count,count)){
                logger.warn("buy process end with error ");
                return;
            }
            logger.warn("buy process end");
            info.setFreeMoney(sellMoney - totalMoney);
            account.addFreeMoney(info.getFreeMoney());
            info.setCreateDate(new Date());
            DB.dealQueue.add(info);
            account.subtractVersion();
        }
    }


}
