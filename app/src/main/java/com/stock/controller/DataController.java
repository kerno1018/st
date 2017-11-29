package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stock.bean.Keys;
import com.stock.entity.AllStockCodeMapping;
import com.stock.service.FluctuateService;
import com.stock.service.HistoryService;
import com.stock.service.StockService;
import com.stock.test.AllStockCodeTest;
import com.stock.test.StockHistoryUpdateTest;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.util.Util;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by kerno on 1/3/2016.
 */
@RestController
public class DataController {
    @Autowired
    private StockService service;
    @Autowired
    private FluctuateService fluctuateService;
    @Autowired
    private HistoryService history;
    @RequestMapping("/history/getAll")
    @ResponseBody
    public String getAllHistory(){
        Map<String,List<Object[]>> map = history.getAll();
        List<Object> result = new LinkedList<>();
        Map<String,ObjectNode> mapp = new HashedMap();

        buildData("20",map.get("20"),mapp);
        buildData("30",map.get("30"),mapp);
        buildData("60",map.get("60"),mapp);
        buildData("120",map.get("120"),mapp);

        for(ObjectNode value : mapp.values()){
            if(DB.currentStockValue.get(value.get("mapping").asText()) != null){
                value.put("now", DB.currentStockValue.get(value.get("mapping").asText().toString()));
                try{
                    value.put("rise20",CacluateUtil.format(value.get("now").asDouble()/value.get("avg20").asDouble()));
                    value.put("rise30",CacluateUtil.format(value.get("now").asDouble()/value.get("avg30").asDouble()));
                    value.put("rise60",CacluateUtil.format(value.get("now").asDouble()/value.get("avg60").asDouble()));
                    value.put("rise120",CacluateUtil.format(value.get("now").asDouble()/value.get("avg120").asDouble()));
                }catch (Exception ex){
                    value.put("rise20",-100);
                    value.put("rise30",-100);
                    value.put("rise60",-100);
                    value.put("rise120",-100);
                }
            }else{
                value.put("now",0.0);
                value.put("rise20",0);
            }
            result.add(value);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "{\"data\":";
        try {
            jsonResult += mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jsonResult+="}";
        return jsonResult;
    }
    private void buildData(String key,List<Object[]> value,Map<String,ObjectNode> storage){
        ObjectNode node = null;
        for(Object[] obj : value){
            String code = obj[1].toString()+obj[0].toString();
            if(storage.get(code) == null){
                node = new ObjectMapper().createObjectNode();
                storage.put(code,node);
            }
            storage.get(code).put("code",obj[0].toString());
            storage.get(code).put("mapping",code);
            if(StringUtils.isEmpty(obj[2])){
                storage.get(code).put("avg"+key,0.0);
            }else {
                storage.get(code).put("avg"+key,CacluateUtil.format(Double.valueOf(obj[2].toString())));
            }
        }
    }
//    @RequestMapping("/data/init")
//    public void initStockHistoryValue(){
////        List<AllStockCodeMapping> stockList = service.getAllStockMap();
////        if(stockList != null && stockList.size()>0){
////            System.out.println("already exists data no need init ");
////            return;
////        }
//        AllStockCodeTest test = new AllStockCodeTest();
//        try {
//            test.initStock(service);
//            test.updateHistoryValue();
//            test.updateFailedStock();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping("/data/update")
    public void updateTodayStockHistoryValue(){
        new Thread(new StockHistoryUpdateTest(service)).start();
    }
    @RequestMapping("/data/motion")
    public void updateMonthValue(){
        fluctuateService.getFluctuateData();
        fluctuateService.getMonthData();
    }

    @RequestMapping("/recommend/today")
    @ResponseBody
    public  List<String> showTodayData(){
        double highPrice = 0.0;
        double lowPrice = 0.0;
        double openPrice = 0.0;
        double closePrice = 0.0;
        List<String> result = new ArrayList<>();
        for (AllStockCodeMapping map : service.getAllStockMap()) {
            String stock = Util.getResponseByUrl(Keys.URL_STOCKK + map.getMapping() + map.getCode());
            if (stock.length() < 30) {
                // TODO fix error code
                continue;
            }
            String id = stock.substring(13, stock.indexOf("="));
            if (stock.startsWith("var hq_str_hkHSI=")) {
                stock = stock.replaceAll("Hang Seng Main Index,", "");
            }
            if (stock.startsWith("var hq_str_hkHSCEI=")) {
                stock = stock.replaceAll("Hang Seng China Enterprises Index,", "");
            }
            int prex = 21;
            if ("HSI".equals(id)) {
                System.out.println("HSI?");
                prex = 18;
            }
            stock = stock.substring(prex, stock.lastIndexOf("\""));
            String[] fields = stock.split(",");

            openPrice = Double.valueOf(fields[1]);
            highPrice = Double.valueOf(fields[4]);
            closePrice = Double.valueOf(fields[3]);
            lowPrice = Double.valueOf(fields[5]);

            if (closePrice > openPrice && openPrice > lowPrice && (highPrice - openPrice) / (openPrice - lowPrice) < 2) {

                if ((closePrice - lowPrice) / lowPrice > 0.05) {

                    if ((openPrice - lowPrice) / (closePrice - openPrice) > 3) {
                        result.add(map.getCode());
                    }
                }
            }
        }
        return result;
    }

}
