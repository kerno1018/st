package com.stock.job.util;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kerno on 2016/2/24.
 */
public class ConditionUtil {
    private static Logger logger = LoggerFactory.getLogger(ConditionUtil.class);
    public static Boolean checkAB(Account account, BaseFund ownFund, BaseFund adviseFund, Double ownYJ, Double adviseYJ){
        if(account.getOwnStock().get(ownFund.getFundB_id()) == null){
            return false;
        }
        // will skip check A if is Strategy B
        if (account.getOwnStock().get(ownFund.getFundA_id()) == null) {
            return false;
        }
        if (account.getOwnStock().get(ownFund.getFundA_id()).getCanUseCount() < Keys.FORBID_SELL_THRESHOLD) {
            if (Keys.SHOW_LOG) {
                logger.info(" - check - " + account.getId() + "ownA count less than 100. will skip this time");
            }
            return false;
        }

        if (ownFund.getMarketA().getAllBuyCount() * ownFund.getMarketA().getBuyTwoPrice() <= Keys.FORBID_ROLLING_LIMIT) {
            if (Keys.SHOW_LOG) {
                logger.info(" - check - " + account.getId() + "ownA volume less than $5000. will skip this time");
            }
            return false;
        }

        if (adviseFund.getMarketA().getAllSellCount() * adviseFund.getMarketA().getSellTwoPrice() <= Keys.FORBID_ROLLING_LIMIT) {
            if (Keys.SHOW_LOG) {
                logger.info(" - check - " + account.getId() + "adviseA volume less than $5000. will skip this time");
            }
            return false;
        }

        // duplicate check
        if (ownFund.getMarketA().getBuyTwoPrice() == 0.0 || ownFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + "ownFund " + ownFund.getFundA_id() + " high limit will skip this time");
            return false;
        }
        // duplicate check
        if (adviseFund.getMarketA().getBuyTwoPrice() == 0.0 || adviseFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + " advise " + adviseFund.getFundA_id() + " high limit will skip this time");
            return false;
        }
        if(account.getOwnStock().get(ownFund.getFundB_id()).getCanUseCount() < Keys.FORBID_SELL_THRESHOLD){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + "ownB count less than 100. will skip this time");
            }
            return false;
        }
        if(ownFund.getMarketB().getAllBuyCount() * ownFund.getMarketB().getBuyTwoPrice() <= Keys.FORBID_ROLLING_LIMIT){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + "ownB volume less than $5000. will skip this time");
            }
            return false;
        }
        if(adviseFund.getMarketB().getAllSellCount() * adviseFund.getMarketB().getSellTwoPrice() <= Keys.FORBID_ROLLING_LIMIT){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + "adviseB volume less than $5000. will skip this time");
            }
            return false;
        }
        if (ownYJ == 100.0) {
            logger.info(" - check - "+account.getId() + "ownFund "+ownFund.getFundA_id()+" high limit will skip this time");
            return false;
        }
        if (adviseYJ == 100.0) {
            logger.info(" - check - "+account.getId() + " advise "+adviseFund.getFundA_id()+" high limit will skip this time");
            return false;
        }

        if (adviseYJ == null || ownYJ == null) {
            logger.info(" - check - advise or own yj is null  will skip this time");
            return false;
        }
        return !adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id());
    }

    public static Boolean checkEagerAB(Account account, BaseFund ownFund,BaseFund adviseFund,Double ownYJ,Double adviseYJ){
        if(account.getOwnStock().get(ownFund.getFundB_id()) == null){
            return false;
        }
        // will skip check A if is Strategy B
        if (account.getOwnStock().get(ownFund.getFundA_id()) == null) {
            return false;
        }

        // duplicate check
        if (ownFund.getMarketA().getBuyTwoPrice() == 0.0 || ownFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + "ownFund " + ownFund.getFundA_id() + " high limit will skip this time");
            return false;
        }
        // duplicate check
        if (adviseFund.getMarketA().getBuyTwoPrice() == 0.0 || adviseFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + " advise " + adviseFund.getFundA_id() + " high limit will skip this time");
            return false;
        }
        if (ownYJ == 100.0) {
            logger.info(" - check - "+account.getId() + "ownFund "+ownFund.getFundA_id()+" high limit will skip this time");
            return false;
        }
        if (adviseYJ == 100.0) {
            logger.info(" - check - "+account.getId() + " advise "+adviseFund.getFundA_id()+" high limit will skip this time");
            return false;
        }

        if (adviseYJ == null || ownYJ == null) {
            logger.info(" - check - advise or own yj is null  will skip this time");
            return false;
        }
        return !adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id());
    }

    public static Boolean checkB(Account account, BaseFund ownFund,BaseFund adviseFund,Double ownYJ,Double adviseYJ){
        if(account.getOwnStock().get(ownFund.getFundB_id()) == null){
            return false;
        }
        // duplicate check
        if (ownFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + "ownFund " + ownFund.getFundB_id() + " high limit will skip this time");
            return false;
        }
        // duplicate check
        if (adviseFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + " advise " + adviseFund.getFundB_id() + " high limit will skip this time");
            return false;
        }

        if(account.getOwnStock().get(ownFund.getFundB_id()).getCanUseCount() * ownFund.getMarketB().getBuyTwoPrice() < Keys.FORBID_SELL_THRESHOLD){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + "ownB money less than $100. will skip this time");
            }
            return false;
        }
        if(ownFund.getMarketB().getAllBuyCount() * ownFund.getMarketB().getBuyTwoPrice() <= Keys.FORBID_ROLLING_LIMIT){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + "ownB volume less than $5000. will skip this time");
            }
            return false;
        }
        if(adviseFund.getMarketB().getAllSellCount() * adviseFund.getMarketB().getSellTwoPrice() <= Keys.FORBID_ROLLING_LIMIT){
            if(Keys.SHOW_LOG){
                logger.info(" - check - "+account.getId() + " adviseB volume less than $5000. will skip this time");
            }
            return false;
        }
        if (ownYJ == 100.0) {
            logger.info(" - check - "+account.getId() + "ownFund "+ownFund.getFundA_id()+" high limit will skip this time");
            return false;
        }
        if (adviseYJ == 100.0) {
            logger.info(" - check - "+account.getId() + " advise "+adviseFund.getFundA_id()+" high limit will skip this time");
            return false;
        }

        if (adviseYJ == null || ownYJ == null) {
            logger.info(" - check - advise or own yj is null  will skip this time");
            return false;
        }
        return !adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id());
    }
    public static Boolean checkEagerB(Account account, BaseFund ownFund,BaseFund adviseFund,Double ownYJ,Double adviseYJ){
        if(account.getOwnStock().get(ownFund.getFundB_id()) == null){
            return false;
        }
        // duplicate check
        if (ownFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + "ownFund " + ownFund.getFundB_id() + " high limit will skip this time");
            return false;
        }
        // duplicate check
        if (adviseFund.getMarketB().getBuyTwoPrice() == 0.0) {
            logger.info(" - check - " + account.getId() + " advise " + adviseFund.getFundB_id() + " high limit will skip this time");
            return false;
        }

        if (ownYJ == 100.0) {
            logger.info(" - check - "+account.getId() + "ownFund "+ownFund.getFundA_id()+" high limit will skip this time");
            return false;
        }
        if (adviseYJ == 100.0) {
            logger.info(" - check - "+account.getId() + " advise "+adviseFund.getFundA_id()+" high limit will skip this time");
            return false;
        }

        if (adviseYJ == null || ownYJ == null) {
            logger.info(" - check - advise or own yj is null  will skip this time");
            return false;
        }

        return !adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id());
    }



}
