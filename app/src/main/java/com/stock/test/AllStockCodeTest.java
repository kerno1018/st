package com.stock.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stock.bean.BaseFund;
import com.stock.entity.AllStockCodeMapping;
import com.stock.entity.StockHistory;
import com.stock.program.fund.Builder;
import com.stock.util.CacluateUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.stock.service.StockService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by kerno on 16-4-10.
 */
public class AllStockCodeTest{
    String ACCESS_TOKEN = "0d9901bb45c5324e603ea76fd857425a59fd5abe22f75ee848dfd638c7602161";
    List<String> stockCode = new LinkedList<>();
    List<String> shCode = new LinkedList<>();
    List<String> szCode = new LinkedList<>();
    List<String> duplicateCode = new LinkedList<>();
    List<String> errorCode = new LinkedList<>();
    public List<String> historySuccess = new LinkedList<>();
    public List<String> historyFailed = new LinkedList<>();
    List<String> historyDoubleCheckSuccess = new LinkedList<>();
    String defaultMethod = "getMktEqudJY.json";
    StockService service = null;
    HttpClient httpClient;
    //    @Before
    public void initStock(StockService service) throws IOException {
        this.service = service;
         httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Authorization", "Bearer " + ACCESS_TOKEN));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        httpClient.getParams().setConnectionManagerTimeout(3000);
    }

    public void showSecId(List<String> ids) throws IOException {
        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Authorization", "Bearer " + ACCESS_TOKEN));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int i=0;

        for(String  key : ids){
            String url="https://api.wmcloud.com:443/data/v1/api/master/getSecID.json?field=&assetClass=&ticker="+key+"&partyID=&cnSpell=";
            GetMethod httpGet = new GetMethod(url);
            httpClient.executeMethod(httpGet);
            String str =  httpGet.getResponseBodyAsString();
            httpGet.releaseConnection();
            ArrayNode nodes = (ArrayNode) new ObjectMapper().readTree(str).get("data");
            if(nodes == null){
                System.out.println(key);
            }else if(nodes.size()>=2){
                System.out.println(str);
            }
        }
    }


    public boolean showSHHistory(String key,Integer stockId) throws IOException {
            String url = "https://api.wmcloud.com:443/data/v1/api/market/"+defaultMethod+"?field=&secID="+key+".XSHG&startDate=&endDate=";
    //        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktEqudJY.json?field=&secID=600000.XSHG&startDate=&endDate=";
            GetMethod httpGet = new GetMethod(url);
            //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
            httpClient.executeMethod(httpGet);
            InputStream is = httpGet.getResponseBodyAsStream();
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String tempLine = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (tempLine != null) {
                builder.append(tempLine);
                tempLine = br.readLine();
            }
            br.close();
            ir.close();
            is.close();
            try{
                ArrayNode nodes = (ArrayNode) new ObjectMapper().readTree(builder.toString()).get("data");
                if(nodes == null){
                    return false;
                }else{
                    historySuccess.add(key);
                    saveHistoryRecord(nodes,stockId);
                    return true;
                }
            }catch (Exception ex){
                System.out.println(key);
                System.out.println(builder.toString());
                return true;
            }finally {
                httpGet.releaseConnection();
            }
    }

    public boolean showSZHistory(String key,Integer stockId) throws IOException {

        String url = "https://api.wmcloud.com:443/data/v1/api/market/"+defaultMethod+"?field=&secID="+key+".XSHE&startDate=&endDate=";
        //        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktEqudJY.json?field=&secID=600000.XSHG&startDate=&endDate=";
        GetMethod httpGet = new GetMethod(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpClient.executeMethod(httpGet);
        InputStream is = httpGet.getResponseBodyAsStream();
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(ir);
        String tempLine = br.readLine();
        StringBuilder builder = new StringBuilder();
        while (tempLine != null) {
            builder.append(tempLine);
            tempLine = br.readLine();
        }
        br.close();
        ir.close();
        is.close();
        try{
            ArrayNode nodes = (ArrayNode) new ObjectMapper().readTree(builder.toString()).get("data");
            if(nodes == null){
                return false;
            }else{
                historySuccess.add(key);
                saveHistoryRecord(nodes,stockId);
                return true;
            }
        }catch (Exception ex){
            System.out.println(key);
            System.out.println(builder.toString());
            return true;
        }finally {
            httpGet.releaseConnection();
        }
    }
    public boolean showIndexHistory(String key,Integer stockId) throws IOException {

        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktIdxd.json?field=&beginDate=&endDate=&indexID=&ticker="+key+"&tradeDate=";
        //        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktEqudJY.json?field=&secID=600000.XSHG&startDate=&endDate=";
        GetMethod httpGet = new GetMethod(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpClient.executeMethod(httpGet);
        InputStream is = httpGet.getResponseBodyAsStream();
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(ir);
        String tempLine = br.readLine();
        StringBuilder builder = new StringBuilder();
        while (tempLine != null) {
            builder.append(tempLine);
            tempLine = br.readLine();
        }
        br.close();
        ir.close();
        is.close();
        try{
            ArrayNode nodes = (ArrayNode) new ObjectMapper().readTree(builder.toString()).get("data");
            if(nodes == null){
                return false;
            }else{
                historySuccess.add(key);
                saveIndexRecord(nodes,stockId);
                return true;
            }
        }catch (Exception ex){
            System.out.println(key);
            System.out.println(builder.toString());
            return true;
        }finally {
            httpGet.releaseConnection();
        }
    }

    private void saveHistoryRecord(ArrayNode nodes,Integer stockId){
        for(JsonNode node : nodes){
            StockHistory history = new StockHistory();
            history.setStockId(stockId);
            Iterator<String> fields = node.fieldNames();
            while(fields.hasNext()){
                String key = fields.next();
                Double value = 0.0;
                try{
                    value = 0.0;
                    Double turnOver = 0.0;
                    if(key.toLowerCase().equals("openprice")){
                        value = node.get(key).doubleValue();
                        history.setOpenPrice(value);
                    }else if(key.toLowerCase().equals("highestprice")) {
                        value = node.get(key).doubleValue();
                        history.setHighestPrice(value);
                    }else if(key.toLowerCase().equals("lowestprice")) {
                        value = node.get(key).doubleValue();
                        history.setLowestPrice(value);
                    }else if(key.toLowerCase().equals("closeprice")) {
                        value = node.get(key).doubleValue();
                        history.setClosePrice(value);
                    }else if(key.toLowerCase().equals("turnovervol")) {
                        turnOver = node.get(key).doubleValue();
                        history.setTurnOverVolume(turnOver);
                    }else if(key.toLowerCase().equals("turnovervalue")) {
                        turnOver = node.get(key).doubleValue();
                        history.setTurnOverValue(turnOver);
                    }else if(key.toLowerCase().equals("tradedate")) {
                        Date date = CacluateUtil.format(node.get(key).asText());
                        history.setTradeDate(date);
                    }
                }catch (Exception ex){
                    throw ex;
                }
            }

            service.saveHistory(history);
        }
    }
    private void saveIndexRecord(ArrayNode nodes,Integer stockId){
        for(JsonNode node : nodes){
            StockHistory history = new StockHistory();
            history.setStockId(stockId);
            Iterator<String> fields = node.fieldNames();
            while(fields.hasNext()){
                String key = fields.next();
                Double value = 0.0;
                Double turnOver =0.0;
                try{
                    if(key.toLowerCase().equals("openindex")){
                        value = node.get(key).doubleValue();
                        history.setOpenPrice(value);
                    }else if(key.toLowerCase().equals("highestindex")) {
                        value = node.get(key).doubleValue();
                        history.setHighestPrice(value);
                    }else if(key.toLowerCase().equals("lowestindex")) {
                        value = node.get(key).doubleValue();
                        history.setLowestPrice(value);
                    }else if(key.toLowerCase().equals("closeindex")) {
                        value = node.get(key).doubleValue();
                        history.setClosePrice(value);
                    }else if(key.toLowerCase().equals("turnovervol")) {
                        turnOver = node.get(key).doubleValue();
                        history.setTurnOverVolume(turnOver);
                    }else if(key.toLowerCase().equals("turnovervalue")) {
                        turnOver = node.get(key).doubleValue();
                        history.setTurnOverValue(turnOver);
                    }else if(key.toLowerCase().equals("tradedate")) {
                        Date date = CacluateUtil.format(node.get(key).asText());
                        history.setTradeDate(date);
                    }
                }catch (Exception ex){
                    throw ex;
                }
            }

            service.saveHistory(history);
        }
    }

    public void updateHistoryValue() throws IOException {

        // stock
        List<AllStockCodeMapping> list = service.getFailedSyncRecord();
        List<AllStockCodeMapping> failed = new LinkedList<>();
        System.out.println(" all count is :" + list.size());
        int i=0;
        for(AllStockCodeMapping stock : list){
            System.out.println(++i);
            if(i%10 == 0){
                service.flushSession();
            }
            defaultMethod = "getMktEqudJY.json";
            if("sh".equals(stock.getMapping())){
                boolean pass = showSHHistory(stock.getCode(),stock.getId());
                if(!pass){
                    defaultMethod = "getMktFundd.json";
                    pass = showSHHistory(stock.getCode(),stock.getId());
                    if(!pass){
                        failed.add(stock);
                        historyFailed.add(stock.getCode());
                    }
                }
            }
            if("sz".equals(stock.getMapping())){
                boolean pass = showSZHistory(stock.getCode(),stock.getId());
                if(!pass){
                    defaultMethod = "getMktFundd.json";
                    pass = showSZHistory(stock.getCode(),stock.getId());
                    if(!pass){
                        failed.add(stock);
                        historyFailed.add(stock.getCode());
                    }
                }
            }

        }
    }

    public void updateFailedStock() throws IOException {
        List<AllStockCodeMapping> list = service.getAllStockMap();
        List<String> success = new ArrayList<>();
        for(String key :historyFailed){
            for(AllStockCodeMapping map : list){
                if(map.getCode().equals(key)){
                   boolean pass = showIndexHistory(map.getCode(),map.getId());
                    if(pass){
                        success.add(map.getCode());
                    }
                }
            }
        }
        System.out.println("remove failed code count : " + success.size());
        for(String key : success){
            System.out.println(key);
        }
        historyFailed.removeAll(success);

    }

    public void saveAllStock() throws IOException {
        stockCode = new StockCodeUtil().buildStockCodeList();
        Builder builder = new Builder();
        builder.build();
        List<BaseFund> list = builder.getBaseFunds();
        for(BaseFund fund: list){
            if(!stockCode.contains(fund.getIndex_id())){
                stockCode.add(fund.getIndex_id());
            }
        }
        System.out.println(stockCode.size());
        for(int i=0;i<stockCode.size();i++){
            service.autoMappingStock(stockCode.get(i));
        }
    }
}
