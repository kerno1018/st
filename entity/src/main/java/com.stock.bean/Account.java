package com.stock.bean;

import com.stock.entity.LogInfo;
import com.stock.util.ClientFactory;
import com.stock.util.Util;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kerno on 16-1-6.
 */
public class Account {
    private String id;
    private volatile String cookie;
    private volatile Double canUseMoney;
    private String email;
    private volatile Boolean status;
    private Map<String,OwnStock> ownStock;
    private LogInfo log;
    private List<OrderInfo> dealOrderSuccess = new ArrayList<>();
    private String dealUrl;
    private volatile Double freeMoney=0.0;
    private volatile Integer version=0;
    private Logger logger = LoggerFactory.getLogger(Account.class);
    private volatile HttpClient client;
    private volatile Integer lockAccountVersion=0;
    private volatile Integer syncMoneyVersion=0;

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public synchronized Double getFreeMoney() {
        Double result = freeMoney;
        freeMoney=0.0;
        return result;
    }

    public synchronized void addFreeMoney(Double freeMoney) {
        this.freeMoney += freeMoney;
    }

    private Boolean dealStragetyRoll = false;

    public Boolean getDealStragetyRoll() {
        return dealStragetyRoll;
    }

    public void setDealStragetyRoll(Boolean dealStragetyRoll) {
        this.dealStragetyRoll = dealStragetyRoll;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public synchronized Double getCanUseMoney() {
        return canUseMoney;
    }

    public synchronized void setCanUseMoney(Double canUseMoney) {
        this.canUseMoney = canUseMoney;
    }

    public Map<String, OwnStock> getOwnStock() {
        return ownStock;
    }

    public void setOwnStock(Map<String, OwnStock> ownStock) {
        this.ownStock = ownStock;
    }

    public LogInfo getLog() {
        return log;
    }

    public void setLog(LogInfo log) {
        this.log = log;
    }


    public void initClient(){
        client  = ClientFactory.getClient(cookie);
        new Thread(new SyncSuccessDeal()).start();
    }

    public List<OrderInfo> getDealOrderSuccess() {
        return dealOrderSuccess;
    }

    public void setDealOrderSuccess(List<OrderInfo> dealOrderSuccess) {
        this.dealOrderSuccess = dealOrderSuccess;
    }

    public String getDealUrl() {
        return dealUrl;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public synchronized void addVersion() {
        ++version;
    }
    public synchronized void subtractVersion() {
        --version;
    }

    public void substractCanUseMoney(double money) {
        setCanUseMoney(canUseMoney - money);
    }

    class SyncSuccessDeal implements Runnable{
        @Override
        public void run() {
            while (true){
                if(Keys.FORCE_STOP){
                    break;
                }
                if(version > 0){
                    if(Keys.SHOW_LOG){
                        logger.warn("sync deal order...");
                    }
                    List<OrderInfo> result = Util.getTurnOverInfo(client,id,"",null);
                    if(result == null){
                        break;
                    }else{
                        setDealOrderSuccess(result);
                    }
                }
                if(syncMoneyVersion > 0){
                    syncMoneyVersion--;
                    logger.warn("sync can use money...");
                    Double money = Util.updateAccountCanUseMoney(client,id);
                    if(money != null){
                        setCanUseMoney(money);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized Integer getLockAccountVersion() {
        return lockAccountVersion;
    }

    public synchronized void setLockAccountVersion(Integer lockAccountVersion) {
        this.lockAccountVersion = lockAccountVersion;
    }

    public synchronized void syncMoney() {
        this.syncMoneyVersion++;
    }
}
