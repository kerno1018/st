//package com.stock.job;
//
//
//import com.stock.bean.BaseFund;
//import com.stock.bean.Keys;
//import com.stock.entity.AutoOrder;
//import com.stock.entity.PickMoney;
//import com.stock.job.util.DateUtil;
//import com.stock.service.PickMoneyService;
//import com.stock.util.CacluateUtil;
//import com.stock.util.DB;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import program.AutoBuyCommand;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by kerno on 1/14/2016.
// */
//@Component
//public class PickMoneyJob {
//    private Logger logger = LoggerFactory.getLogger(PickMoneyJob.class);
//    @Autowired
//    private PickMoneyService service;
//
//    @Scheduled(cron="0/5 * 08-15 * * ? ")
//    public void save(){
//        if(!DateUtil.isValidTime()){
//            DB.pickMoneyQueue.clear();
//            return;
//        }
//        if(DB.pickMoneyQueue.size()>0){
//            PickMoney money = DB.pickMoneyQueue.poll();
//            service.save(money);
//        }
//    }
//    @Scheduled(cron="0/10 * 09-10 * * ? ")
//    public void rolling(){
//        if(!DateUtil.isValidTime() || DB.adviseZQFund.size() == 0){
//            return;
//        }
//        if ( !Keys.PICK_MONEY_UPDATE_DONE){
//            List<PickMoney> result = service.getYesterdayPickMoneyList();
//            if(result == null || result.size() == 0){
//                Keys.PICK_MONEY_UPDATE_DONE = true;
//            }
//            if(result != null){
//
//                for(PickMoney bean : result){
//
//                    if (DB.getMj(bean.getFund()) != null){
//                        BaseFund fund = null;
//                        try {
//                            fund = DB.getMj(bean.getFund()).clone();
//                            fund.calculate();
//                        } catch (CloneNotSupportedException e) {
//                            e.printStackTrace();
//                        }
//
//                        if(fund.getSecuritiesIndexIncrease() == null || fund.getSellPremium() == null || fund.getMarketB() == null || fund.getMarketA() == null){
//                            continue;
//                        }
//
//                        bean.setSellSII(fund.getSecuritiesIndexIncrease());
//                        bean.setSellPremium(fund.getSellPremium());
//                        bean.setSellPriceOneA(fund.getMarketA().getBuyOnePrice());
//                        bean.setSellPriceOneB(fund.getMarketB().getBuyOnePrice());
//                        bean.setSellPriceTwoA(fund.getMarketA().getBuyTwoPrice());
//                        bean.setSellPriceTwoB(fund.getMarketB().getBuyTwoPrice());
//                        bean.setSellOneVolumeA(fund.getMarketA().getBuyOne());
//                        bean.setSellOneVolumeB(fund.getMarketB().getBuyOne());
//                        bean.setSellTwoVolumeA(fund.getMarketA().getBuyTwo());
//                        bean.setSellTwoVolumeB(fund.getMarketB().getBuyTwo());
//                        service.update(bean);
//                    }
//
//                }
//
//            }
//
//        }
//
//
//    }
//
//
//
//}
