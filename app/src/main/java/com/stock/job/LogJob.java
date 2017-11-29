package com.stock.job;

import com.stock.bean.BaseFund;
import com.stock.bean.Group;
import com.stock.entity.LogInfo;
import com.stock.job.util.DateUtil;
import com.stock.log.EagerLogRunner;
import com.stock.log.LogRunner;
import com.stock.service.UserService;
import com.stock.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class LogJob {
    private Logger logger = LoggerFactory.getLogger(LogJob.class);
    @Autowired
    private UserService service;

    @Scheduled(cron="0/30 * 09-16  * * ? ")
    public void recordLog() {
        if(DB.dealQueue.size() > 0){
            logger.info("save deal log start");
            LogInfo log = DB.dealQueue.poll();
            service.saveLog(log);
            logger.info("save deal log end");
        }
    }

    @Scheduled(cron="0/2 * 09-15 * * ? ")
    public void version1Log(){
        if(!DateUtil.isValidTime()){
            return;
        }
        if(DB.adviseZQFund.size() ==0){
            return;
        }
        for(Group group : DB.getAllStrategy().values()) {
            if(group.getType().getId() >= 30 || DB.adviseZQFund.get(group.getType().getId() + "") == null){
                continue;
            }
            List<BaseFund> fund = new ArrayList<>();
            for (int i = 0; i < DB.adviseZQFund.get(group.getType().getId() + "").size(); i++) {
                try {
                    fund.add(DB.adviseZQFund.get(group.getType().getId() + "").get(i).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            for (int own = 0; own < fund.size(); own++) {
                BaseFund ownFund = fund.get(own);
                Double ownYJ = ownFund.getBuyPremium();
                Double ownYJ2 = ownFund.getCurrentBuyTwoPremium();
                for (int advise = 0; advise < fund.size(); advise++) {
                    BaseFund adviseFund = fund.get(advise);
                    Double adviseYJ = adviseFund.getSellPremium();
                    Double adviseYJ2 = adviseFund.getCurrentSellTwoPremium();

                    if (adviseYJ == null || ownYJ == null) {
                        continue;
                    }
                    if (ownYJ == 100.0) {
                        continue;
                    }
                    if (adviseYJ == 100.0) {
                        continue;
                    }
                    if (adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id())) {
                        continue;
                    }
                    // five count
                    if ((ownYJ - adviseYJ >= 0.1) && (ownYJ2 - adviseYJ2) > 0) {
                        new Thread(new LogRunner(adviseFund, ownFund)).start();
                    }
                }
            }
        }
    }
    @Scheduled(cron="0/2 * 09-15 * * ? ")
    public void version2Log(){
        if(!DateUtil.isValidTime()){
            return;
        }
        if(DB.adviseZQFund.size() ==0){
            return;
        }
//        if(StringUtils.isEmpty(Keys.RANDOM_MJ)){
//            System.out.println("RANDOM_MJ code is empty");
//            return;
//        }
        for(Group group : DB.getAllStrategy().values()) {
            if(group.getType().getId() >= 30 || DB.adviseZQFund.get(group.getType().getId() + "") == null){
                continue;
            }
            List<BaseFund> fund = new ArrayList<>();
            for (int i = 0; i < DB.adviseZQFund.get(group.getType().getId() + "").size(); i++) {
                try {
                    fund.add(DB.adviseZQFund.get(group.getType().getId() + "").get(i).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            for (int own = 0; own < fund.size(); own++) {
                BaseFund ownFund = fund.get(own);
                Double ownYJ = ownFund.getBuyPremium();
                for (int advise = 0; advise < fund.size(); advise++) {
                    BaseFund adviseFund = fund.get(advise);
                    Double adviseYJ = adviseFund.getEagerSellOnePreminum();

                    if (adviseYJ == null || ownYJ == null) {
                        continue;
                    }
                    if (ownYJ == 100.0) {
                        continue;
                    }
                    if (adviseYJ == 100.0) {
                        continue;
                    }
                    if (adviseFund.getBase_fund_id().equals(ownFund.getBase_fund_id())) {
                        continue;
                    }
                    // five count
                    if ((ownYJ - adviseYJ >= 0.1)) {
                        new Thread(new EagerLogRunner(adviseFund, ownFund)).start();
                    }
                }
            }
        }
    }
}
