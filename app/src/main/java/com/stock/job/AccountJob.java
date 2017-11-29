package com.stock.job;

import com.stock.bean.Account;
import com.stock.bean.Keys;
import com.stock.bean.OwnStock;
import com.stock.context.SpringContext;
import com.stock.entity.User;
import com.stock.job.util.DateUtil;
import com.stock.service.UserService;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.util.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.HeartUtil;
import com.stock.util.Util;

import java.util.*;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class AccountJob {
    private Logger logger = LoggerFactory.getLogger(AccountJob.class);
    @Autowired
    private SpringContext context;

    @Scheduled(cron="0/4 * 09-15 * * ? ")
    public void addAccount(){
        if(DateUtil.isValidDay()){
            UserService service = context.getBean("service.UserService");
            List<User> list = service.getActiveUsers();
            if(list != null ){
                Account account = null;
                for(User user : list){
                    if(null != DB.getAccount(user.getId().toString())){
                        if(!DB.getAccount(user.getId().toString()).getStatus() && DB.getAccount(user.getId().toString()).getLockAccountVersion() == 1){
                            logger.warn("reset cookie and init client start");
                            DB.getAccount(user.getId()+"").setCookie(user.getAccount().getCookie());
                            DB.getAccount(user.getId()+"").initClient();
                            DB.getAccount(user.getId()+"").setLockAccountVersion(0);
                            DB.getAccount(user.getId()+"").setStatus(true);
                            logger.warn("reset cookie and init client done");
                            try {
                                Sender.send(Keys.needProxy,account.getEmail(),"Auto Fix Timeout Success.","Success" + CacluateUtil.format(new Date()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        continue;
                    }
                    logger.warn("init account "+ user.getUsername());
                    account = new Account();
                    account.setDealUrl(Keys.GTJA_DEALINFO_TADAY);
                    account.setId(user.getId()+"");
                    account.setEmail(user.getEmail());
                    account.setStatus(user.getEnable());
                    account.setCookie(user.getAccount().getCookie());
                    logger.warn("init client");
                    account.initClient();
                    logger.warn("sync stock info");
                    List<OwnStock> ownStocks = Util.getOwnStock(account.getClient(),account);
                    Map<String,OwnStock> map = new HashMap<>();
                    if(ownStocks == null){
                        logger.warn("own stock is null  will skip this account user:" + user.getId());
                        service.lockAccount(user);
                        continue;
                    }
                    for(OwnStock stock : ownStocks){
                        map.put(stock.getId(),stock);
                    }
                    account.setOwnStock(map);
                    logger.warn("add account "+ account.getId());
                    try {
                        Sender.send(Keys.needProxy,account.getEmail(),"Auto Login Success.","Success" + CacluateUtil.format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    account.syncMoney();
                    DB.addAccount(account);
                }
            }
        }
    }

    @Scheduled(cron="0/29 * 08-15 * * ? ")
    public void keepHeart(){
        Map<String, Account> accounts =  DB.getAllAccount();
        if(accounts.size() > 0 ){
            for(Account account : accounts.values()){
                if(account.getStatus()){
                    logger.info("account "+account.getId()+" keep heart...... ");
                    HeartUtil.heart(account);
                }
            }
        }
    }
}
