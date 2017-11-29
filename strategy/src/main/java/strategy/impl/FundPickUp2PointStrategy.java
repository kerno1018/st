package strategy.impl;

import com.stock.bean.BaseFund;
import com.stock.bean.Keys;
import com.stock.entity.PickMoney;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.util.Sender;
import strategy.Strategy;
import util.compator.BaseFundCompare;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by kerno on 1/4/2016.
 */
public class FundPickUp2PointStrategy extends Strategy {
//    Logger logger = LoggerFactory.getLogger(Fund2PointStrategy.class);

    @Override
    public void advise(List<BaseFund> funds, Integer type) {
        if(funds != null && funds.size() >0){
            Collections.sort(funds,new BaseFundCompare());
            BaseFund advise = null;
            try {
                advise = funds.get(0).clone();
                advise.calculate();
                if(advise.getSellPremium() <= -1.5){
                    PickMoney pick = new PickMoney();
                    pick.setBuyOneVolumeA(advise.getMarketA().getBuyOne());
                    pick.setBuyPriceOneA(advise.getMarketA().getBuyOnePrice());
                    pick.setBuyTwoVolumeA(advise.getMarketA().getBuyTwo());
                    pick.setBuyPriceTwoA(advise.getMarketA().getBuyTwoPrice());
                    pick.setBuyOneVolumeB(advise.getMarketB().getBuyOne());
                    pick.setBuyPriceOneB(advise.getMarketB().getBuyOnePrice());
                    pick.setBuyTwoVolumeB(advise.getMarketB().getBuyTwo());
                    pick.setBuyPriceTwoB(advise.getMarketB().getBuyTwoPrice());
                    pick.setBuySII(advise.getSecuritiesIndexIncrease());
                    pick.setBuyPremium(advise.getSellPremium());
                    pick.setCreateTIME(new Date());
                    pick.setFund(advise.getBase_fund_id());
                    DB.pickMoneyQueue.add(pick);
                }
            } catch (Exception e) {
                System.out.println("fundId: " + advise.getBase_fund_id());
                System.out.println("sellOne premium: " + advise.getCurrentSellOnePremium());
                System.out.println("sellPremium premium: " + advise.getSellPremium());
                System.out.println("buyone premium: " + advise.getCurrentBuyOnePremium());
                System.out.println("securitiesIndexIncrease:"+advise.getSecuritiesIndexIncrease());
                System.out.println("fundNetValue:"+advise.getFundNetValue());
                for(BaseFund fund : funds){
                    System.out.println(fund.getBase_fund_id());
                }
                e.printStackTrace();
            }

        }
    }

}
