package sell;

import com.stock.bean.*;
import com.stock.entity.LogInfo;
import com.stock.util.DB;
import com.stock.util.JudgeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kerno on 16-2-3.
 */
public class DealLogBuilder {
    private static Logger logger = LoggerFactory.getLogger(DealLogBuilder.class);
    public static void buildSellAInfo(LogInfo info, Account account, OwnStock ownA, BaseFund ownFund, Stock sellA){
        if(Keys.DEBUG){
            return;
        }
        info.setUserId(Integer.valueOf(account.getId()));
        info.setSellStockA(ownA.getId());
        if(info.getTheorySellCount() != null){
            info.setTheorySellCount(info.getTheorySellCount() + ownA.getCanUseCount());
        }else{
            info.setTheorySellCount(ownA.getCanUseCount()+"");
        }
        info.setSellOneA(sellA.getBuyOne());
        info.setSellTwoA(sellA.getBuyTwo());
        info.setSellThreeA(sellA.getBuyThree());
        info.setSellFourA(sellA.getBuyFouro());
        info.setSellFiveA(sellA.getBuyFive());
        info.setSellFundNetValue(ownFund.getFundNetValue());
        info.setSellPriceFiveA(sellA.getBuyFivePrice());
        info.setSellPriceFourA(sellA.getBuyFouroPrice());
        info.setSellPriceThreeA(sellA.getBuyThreePrice());
        info.setSellPriceTwoA(sellA.getBuyTwoPrice());
        info.setSellPriceOneA(sellA.getBuyOnePrice());
        info.setSellPosition(DB.getMj(ownFund.getBase_fund_id()).getPosition());
        info.setSellSII(DB.getMj(ownFund.getBase_fund_id()).getSecuritiesIndexIncrease());
        info.setSellPremiumFive(ownFund.getCurrentBuyFivePremium());
        info.setSellPremiumFour(ownFund.getCurrentBuyFourPremium());
        info.setSellPremiumThree(ownFund.getCurrentBuyThreePremium());
        info.setSellPremiumTwo(ownFund.getCurrentBuyTwoPremium());
        info.setSellPremiumOne(ownFund.getCurrentBuyOnePremium());
    }

    public static void buildSellBInfo(LogInfo info, Account account, OwnStock ownB, BaseFund ownFund, Stock sellB){
        if(Keys.DEBUG){
            return;
        }
        info.setUserId(Integer.valueOf(account.getId()));
        info.setSellStockB(ownB.getId());
        if(info.getTheorySellCount() != null){
            info.setTheorySellCount(info.getTheorySellCount() + ownB.getCanUseCount());
        }else{
            info.setTheorySellCount(ownB.getCanUseCount()+"");
        }
        info.setSellOneB(sellB.getBuyOne());
        info.setSellTwoB(sellB.getBuyTwo());
        info.setSellThreeB(sellB.getBuyThree());
        info.setSellFourB(sellB.getBuyFouro());
        info.setSellFiveB(sellB.getBuyFive());
        info.setSellFundNetValue(ownFund.getFundNetValue());
        info.setSellPriceFiveB(sellB.getBuyFivePrice());
        info.setSellPriceFourB(sellB.getBuyFouroPrice());
        info.setSellPriceThreeB(sellB.getBuyThreePrice());
        info.setSellPriceTwoB(sellB.getBuyTwoPrice());
        info.setSellPriceOneB(sellB.getBuyOnePrice());
        info.setSellPosition(DB.getMj(ownFund.getBase_fund_id()).getPosition());
        info.setSellSII(DB.getMj(ownFund.getBase_fund_id()).getSecuritiesIndexIncrease());
        info.setSellPremiumFive(ownFund.getCurrentBuyFivePremium());
        info.setSellPremiumFour(ownFund.getCurrentBuyFourPremium());
        info.setSellPremiumThree(ownFund.getCurrentBuyThreePremium());
        info.setSellPremiumTwo(ownFund.getCurrentBuyTwoPremium());
        info.setSellPremiumOne(ownFund.getCurrentBuyOnePremium());
    }
    public static void buildBuyInfo(LogInfo info, Account account, BaseFund advise){
        if(Keys.DEBUG){
            return;
        }
        Stock adviseA = advise.getMarketA();
        Stock adviseB = advise.getMarketB();
        info.setUserId(Integer.valueOf(account.getId()));
        info.setBuyStockA(adviseA.getId());
        info.setBuyStockB(adviseB.getId());
        info.setBuyOneA(adviseA.getSellOne());
        info.setBuyOneB(adviseB.getSellOne());
        info.setBuyTwoA(adviseA.getSellTwo());
        info.setBuyTwoB(adviseB.getSellTwo());
        info.setBuyThreeA(adviseA.getSellThree());
        info.setBuyThreeB(adviseB.getSellThree());
        info.setBuyFourA(adviseA.getSellFour());
        info.setBuyFourB(adviseB.getSellFour());
        info.setBuyFiveA(adviseA.getSellFive());
        info.setBuyFiveB(adviseB.getSellFive());
        info.setBuyFundNetValue(advise.getFundNetValue());
        info.setBuyPriceFiveA( adviseA.getSellFivePrice());
        info.setBuyPriceFourA( adviseA.getSellFourPrice());
        info.setBuyPriceThreeA(adviseA.getSellThreePrice());
        info.setBuyPriceTwoA(  adviseA.getSellTwoPrice());
        info.setBuyPriceOneA(  adviseA.getSellOnePrice());
        info.setBuyPriceFiveB( adviseB.getSellFivePrice());
        info.setBuyPriceFourB( adviseB.getSellFourPrice());
        info.setBuyPriceThreeB(adviseB.getSellThreePrice());
        info.setBuyPriceTwoB(  adviseB.getSellTwoPrice());
        info.setBuyPriceOneB(  adviseB.getSellOnePrice());
        info.setBuyPosition(DB.getMj(advise.getBase_fund_id()).getPosition());
        info.setBuySII(DB.getMj(advise.getBase_fund_id()).getSecuritiesIndexIncrease());
        info.setBuyPremiumFive(advise.getCurrentSellFivePremium());
        info.setBuyPremiumFour(advise.getCurrentSellFourPremium());
        info.setBuyPremiumThree(advise.getCurrentSellThreePremium());
        info.setBuyPremiumTwo(advise.getCurrentSellTwoPremium());
        info.setBuyPremiumOne(advise.getCurrentSellOnePremium());
    }

    public static int getMinSellVolume(BaseFund ownFund,Account account){
        OwnStock ownB  = account.getOwnStock().get(ownFund.getFundB_id());
        OwnStock ownA  = account.getOwnStock().get(ownFund.getFundA_id());
        Double sellMoney = Keys.SELL_THRESHOLD * 2;
        sellMoney = sellMoney > account.getCanUseMoney() ? account.getCanUseMoney() : sellMoney;
        Double split = 0.0;
        Double ownMaxValue = 0.0;
        if(JudgeUtil.isB()){
            split = sellMoney/(ownFund.getMarketB().getBuyTwoPrice());
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
            ownMaxValue = ownB.getCanUseCount();
        }else if(JudgeUtil.isEagerB()){
            split = sellMoney/(ownFund.getMarketB().getSellOnePrice());
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
            ownMaxValue = ownB.getCanUseCount();
        }else if(JudgeUtil.isAB() || JudgeUtil.isConcurrentAB()){
            split = sellMoney/(ownFund.getMarketA().getBuyTwoPrice() + ownFund.getMarketB().getBuyTwoPrice());
            split = split > ownA.getCanUseCount() ? ownA.getCanUseCount() : split;
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
            ownMaxValue = ownB.getCanUseCount() > ownA.getCanUseCount() ? ownA.getCanUseCount() : ownB.getCanUseCount();
        }else if(JudgeUtil.isEagerAB()){
            split = sellMoney/(ownFund.getMarketA().getSellOnePrice() + ownFund.getMarketB().getSellOnePrice());
            split = split > ownA.getCanUseCount() ? ownA.getCanUseCount() : split;
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
            ownMaxValue = ownB.getCanUseCount() > ownA.getCanUseCount() ? ownA.getCanUseCount() : ownB.getCanUseCount();
        }
//        logger.info(account.getId() + " own " + ownA.getId() + "  real sell count is " +split);
        int minNum = split.intValue();

        if(minNum / (ownMaxValue-minNum) > 3){
            minNum += ownMaxValue - minNum;
        }

        return minNum/100*100;
    }

    public static int getMinBuyVolume( BaseFund advise,BaseFund ownFund,Account account){
        OwnStock ownB  = account.getOwnStock().get(ownFund.getFundB_id());
        OwnStock ownA  = account.getOwnStock().get(ownFund.getFundA_id());
        Double sellMoney = Keys.SELL_THRESHOLD * 2;
        sellMoney = sellMoney > account.getCanUseMoney() ? account.getCanUseMoney() : sellMoney;
        Double split = 0.0;
        if(JudgeUtil.isB()){
            split = sellMoney/(advise.getMarketB().getSellTwoPrice());
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
        }else if(JudgeUtil.isEagerB()){
            split = sellMoney/(advise.getMarketB().getBuyOnePrice());
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
        }else if(JudgeUtil.isAB() || JudgeUtil.isConcurrentAB()){
            split = sellMoney/(advise.getMarketA().getSellTwoPrice() + advise.getMarketB().getSellTwoPrice());
            split = split > ownA.getCanUseCount() ? ownA.getCanUseCount() : split;
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
        }else if(JudgeUtil.isEagerAB()){
            split = sellMoney/(advise.getMarketA().getBuyOnePrice() + advise.getMarketB().getBuyOnePrice());
            split = split > ownA.getCanUseCount() ? ownA.getCanUseCount() : split;
            split = split > ownB.getCanUseCount() ? ownB.getCanUseCount() : split;
        }
        return (int)(split/100)*100;
    }
}
