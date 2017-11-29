package com.stock.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.stock.bean.*;
import com.stock.entity.AutoOrder;
import com.stock.entity.Grid;
import com.stock.entity.LogInfo;
import com.stock.entity.PickMoney;
import org.jsoup.helper.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by kerno on 16-1-4.
 */
public class DB {
    private static Map<String, Stock> tempSource = new ConcurrentHashMap<>();
    private static volatile Map<String, Account> accountMap = new ConcurrentHashMap<>();
    private static Map<Integer, Group> strategyGroup = new ConcurrentHashMap<>();
    private static Map<String,BaseFund> mj = new ConcurrentHashMap<>();
    private static Map<String, Etf> etf = new ConcurrentHashMap<>();
    private static Map<String,String> jjMaping = new ConcurrentHashMap<>(); // sh or sz
    private static Map<String, Stock> stockInfo = new ConcurrentHashMap<>(); // stock info
    public static Set<String> failedSyncStock = new ConcurrentSkipListSet<>();
    public static Map<String,String> stockMapping = new HashMap<>();
    public static Map<String,List<BaseFund>> adviseZQFund = new ConcurrentHashMap<>();
    public static Queue<LogInfo> dealQueue = new ConcurrentLinkedQueue<>();
    public static Map<Integer,AutoOrder> autoOrders = new ConcurrentHashMap<>();
    public static Map<Integer, Grid> grids = new ConcurrentHashMap<>();
    public static Set<String> updateStock = new ConcurrentSkipListSet<>();
    public static Queue<PickMoney> pickMoneyQueue = new ConcurrentLinkedQueue<>();
    public static Map<String,Map<Integer,String>> gridOrder = new ConcurrentHashMap<>();
    public static Map<String,Double> currentStockValue = new HashMap<>();
    public static Map<String,String> allStockMapping = new HashMap<>();


    public static void clear(){
        tempSource.clear();
        accountMap.clear();
        strategyGroup.clear();
        mj.clear();
        jjMaping.clear();
        stockInfo.clear();
        failedSyncStock.clear();
        stockMapping.clear();
        adviseZQFund.clear();
        dealQueue.clear();
        autoOrders.clear();
        grids.clear();
        updateStock.clear();
        pickMoneyQueue.clear();
        gridOrder.clear();
    }

    public static void addStrategy(Group group){
        strategyGroup.put(group.getType().getId(),group);
    }

    public static Group getStrategy(Integer id){
        return strategyGroup.get(id);
    }

    public static Map<Integer,Group> getAllStrategy(){
        return strategyGroup;
    }

    public static BaseFund updateBaseFund(JsonNode item){
        if(item != null){
            JsonNode node = item.path("cell");
            BaseFund fund = new BaseFund();
            fund.setBase_fund_id(node.get("base_fund_id").textValue());
            fund.setBase_fund_nm(node.get("base_fund_nm").textValue());
            fund.setFundA_id(node.get("fundA_id").textValue());
            fund.setFundA_nm(node.get("fundA_nm").textValue());
            fund.setFundB_id(node.get("fundB_id").textValue());
            fund.setFundB_nm(node.get("fundB_nm").textValue());
            fund.setBase_fund_price(node.get("price").asDouble());
            fund.setIndex_id(node.get("index_id").textValue());

            return mj.put(fund.getBase_fund_id(),fund);
        }
        return null;
    }

    public static Map<String, BaseFund> getAllMJ(){
        return mj;
    }

    public static Map<String,Etf>getAllEtf(){
        return etf;
    }


    public static void updateStockInfo(String source){
        String[] stocks = source.split(";");
        Stock fund = null;
        for(String stock : stocks){
            stock = stock.replaceAll("\\\\s*|\\t|\\r|\\n","");
            if(stock.length() < 30){
                // TODO fix error code
                if(!StringUtil.isBlank(stock)){
                    failedSyncStock.add(stock.substring(11,stock.lastIndexOf("=")));
                }
                continue;
            }
            if(stock.startsWith("var hq_str_hkHSI=")){
                stock = stock.replaceAll("Hang Seng Main Index,","");
            }
            if(stock.startsWith("var hq_str_hkHSCEI=")){
                stock = stock.replaceAll("Hang Seng China Enterprises Index,","");
            }
            fund = new Stock(stock);

            stockInfo.put(fund.getId(),fund);
        }
    }

    public static Stock getStockInfo(String id){
        return stockInfo.get(id);
    }

    public static Map<String, Stock> getAllStock(){
        return stockInfo;
    }


    public static Stock getRecord(String baseId){

        return tempSource.get(baseId);
    }

    public static BaseFund getMj(String id) {
        return mj.get(id);
    }

    public static Etf getEtf(String id){
        return etf.get(id);
    }

    public static void setMj(BaseFund base) {
        mj.put(base.getBase_fund_id(),base);
    }

    public static String getMappedId(String id){
        return jjMaping.get(id);
    }
    public static void setMappedId(String id,String value){
        jjMaping.put(id,value);
    }

    public static void addAccount(Account account){
        accountMap.put(account.getId(),account);
    }
    public static Account getAccount(String id){
        return accountMap.get(id);
    }
    public static Map<String, Account> getAllAccount(){
        return accountMap;
    }


}
