//package com.stock.program;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stock.bean.BaseFund;
//import com.stock.bean.Keys;
//import com.stock.entity.StockMap;
//import com.stock.util.DB;
//import com.stock.util.Util;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by kerno on 2016/4/17.
// */
//public class SyncBaseFund {
//
//    public void start(){
//        try {
//            initBaseFund();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initBaseFund() throws IOException {
//        if(DB.getAllMJ().size() >0 ){
//            return;
//        }
//        String source = Util.getResponseByUrl(Keys.STRATEGE_ZQ_ETF_ROLL_URL_MJ);
//        JsonNode node = new ObjectMapper().readTree(source);
//        List<StockMap> result = new LinkedList<>();
//        StockMap stock = null;
//        try{
//            for(JsonNode item : node.path("rows")){
//                if(item != null){
//                    JsonNode chhildNode = item.path("cell");
//                    BaseFund fund = new BaseFund();
//                    stock = new StockMap();
//                    fund.setIndex_id(chhildNode.get("index_id").textValue());
//                    stock.setCode(fund.getIndex_id());
//                    stock.setMapping("sz");
//                    try{
//                        Integer.parseInt(fund.getIndex_id());
//                    }catch (Exception ex){
//                        stock.setMapping("hk");
//                    }
//                    if(!result.contains(stock)){
//                        result.add(stock);
//                    }
//                    stock = new StockMap();
//                    fund.setBase_fund_id(chhildNode.get("base_fund_id").textValue());
//                    fund.setBase_fund_nm(chhildNode.get("base_fund_nm").textValue());
//                    stock.setCode(fund.getBase_fund_id());
//                    stock.setMapping("sz");
//                    if(!result.contains(stock)){
//                        result.add(stock);
//                    }
//                    stock = new StockMap();
//                    fund.setFundA_id(chhildNode.get("fundA_id").textValue());
//                    fund.setFundA_nm(chhildNode.get("fundA_nm").textValue());
//                    stock.setCode(fund.getFundA_id());
//                    stock.setMapping("sz");
//                    if(!result.contains(stock)){
//                        result.add(stock);
//                    }
//                    stock = new StockMap();
//                    fund.setFundB_id(chhildNode.get("fundB_id").textValue());
//                    fund.setFundB_nm(chhildNode.get("fundB_nm").textValue());
//                    fund.setBase_fund_price(chhildNode.get("price").asDouble());
//                    stock.setCode(fund.getFundB_id());
//                    stock.setMapping("sz");
//                    if(!result.contains(stock)){
//                        result.add(stock);
//                    }
//                    DB.getAllMJ().put(fund.getBase_fund_id(),fund);
//                }
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//}
