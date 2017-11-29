package com.stock.job;

import com.stock.bean.Group;
import com.stock.bean.Keys;
import com.stock.entity.User;
import com.stock.job.util.DateUtil;
import com.stock.login.AutoLogin;
import com.stock.service.FluctuateService;
import com.stock.service.StockService;
import com.stock.service.UserService;
import com.stock.test.AllStockCodeTest;
import com.stock.test.StockHistoryUpdateTest;
import com.stock.util.DB;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class SystemJob {
    private Logger logger = LoggerFactory.getLogger(SystemJob.class);
    @Autowired
    private StockService service;
    @Autowired
    private UserService userService;
    @Autowired
    private FluctuateService fluctuateService;

    @Scheduled(cron="0 0 16 * * ? ")
    public void updateStockHistoryValue(){
        new Thread(new StockHistoryUpdateTest(service)).start();
    }

    @Scheduled(cron="0 0 18 * * ? ")
    public void updateMonthValue(){
        fluctuateService.getFluctuateData();
        fluctuateService.getMonthData();
    }


    @Scheduled(cron="0 0 03 * * ? ")
    public void deleteDuplicateRecord(){
        service.deleteDuplicateRecord();
    }


    @Scheduled(cron = "0 5 15 * * ?")
    public void lockAllAccount(){
        // local all user and clear all memory store
        logger.info("lock all accounts");
        userService.lockAllAccount();
        DB.clear();
    }

    @Scheduled(cron = "0 0 07 * * ?")
    public void init(){
        logger.info("system init start...");
        Keys.PICK_MONEY_UPDATE_DONE = false;
        DateUtil.init();
    }

    @Scheduled(cron="0 0/5 08 * * ? ")
    public void updateYesterdayFundNetValue(){
        if(DateUtil.isValidDay()){
            logger.info("update yesterday fund net value start...");
            Group roll = DB.getStrategy(1);
            if(roll != null && DB.getAllMJ().size() > 0){
                try {
                    service.syncYesterdayFundNetValue(roll.getProp1());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(cron="0 0/2 09-15 * * ? ")
    private void auto(){
        if(DateUtil.isValidLoginEndTime()){
            String osName = System.getProperties().get("os.name").toString();
            if(!StringUtil.isBlank(osName) && osName.contains("Linux")){
                List<User> users = userService.getAutoUsers();
                if(users == null || users.size() ==0){
                    return;
                }
                logger.warn(users.get(0).getId() + " auto login start");
                new Thread(new AutoLogin(userService,users.get(0))).start();
            }
        }
    }

    @Scheduled(cron = "0 0 15 * * ? ")
    public void syncMapping(){
        AllStockCodeTest test = new AllStockCodeTest();
        try {
            test.initStock(service);
            test.saveAllStock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
