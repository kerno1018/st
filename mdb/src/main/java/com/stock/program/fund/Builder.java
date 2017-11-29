package com.stock.program.fund;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.stock.bean.BaseFund;
import com.stock.bean.Keys;
import com.stock.entity.StockMap;
import com.stock.program.fund.util.IgnoreFund;
import com.stock.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 2016/4/17.
 */
public class Builder {
    private List<BaseFund> baseFunds = new ArrayList<BaseFund>();
    private List<StockMap> allStock = new ArrayList<StockMap>();

    public void build(){
        String source = Util.getResponseByUrl(Keys.STRATEGE_ZQ_ETF_ROLL_URL_MJ);
        JsonNode node = null;
        System.out.println(Keys.STRATEGE_ZQ_ETF_ROLL_URL_MJ);
        try {
            node = new ObjectMapper().readTree(source);
        } catch (IOException e) {
            System.out.println("source:"+source);
            e.printStackTrace();
        }
        StockMap stock = null;
        try{
            if(node == null || node.path("rows") == null){
                System.out.println("builderror:"+node);
                return;
            }
            for(JsonNode item : node.path("rows")){
                if(item != null){
                    JsonNode chhildNode = item.path("cell");
                    BaseFund fund = new BaseFund();
                    stock = new StockMap();
                    fund.setIndex_id(chhildNode.get("index_id").textValue());
                    if(IgnoreFund.getIgnoreFund().contains(fund.getIndex_id())){
                        continue;
                    }
                    stock.setCode(fund.getIndex_id());
                    stock.setMapping("sz");
                    try{
                        Integer.parseInt(fund.getIndex_id());
                    }catch (Exception ex){
                        stock.setMapping("hk");
                    }
                    if(!allStock.contains(stock)){
                        allStock.add(stock);
                    }
                    stock = new StockMap();
                    fund.setBase_fund_id(chhildNode.get("base_fund_id").textValue());
                    fund.setBase_fund_nm(chhildNode.get("base_fund_nm").textValue());
                    if(IgnoreFund.getIgnoreFund().contains(fund.getBase_fund_id())){
                        continue;
                    }
                    stock.setCode(fund.getBase_fund_id());
                    stock.setMapping("sz");
                    if(!allStock.contains(stock)){
                        allStock.add(stock);
                    }
                    stock = new StockMap();
                    fund.setFundA_id(chhildNode.get("fundA_id").textValue());
                    fund.setFundA_nm(chhildNode.get("fundA_nm").textValue());
                    stock.setCode(fund.getFundA_id());
                    if(IgnoreFund.getIgnoreFund().contains(fund.getFundA_id())){
                        continue;
                    }
                    stock.setMapping("sz");
                    if(!allStock.contains(stock)){
                        allStock.add(stock);
                    }
                    stock = new StockMap();
                    fund.setFundB_id(chhildNode.get("fundB_id").textValue());
                    fund.setFundB_nm(chhildNode.get("fundB_nm").textValue());
                    if(IgnoreFund.getIgnoreFund().contains(fund.getFundB_id())){
                        continue;
                    }
                    fund.setBase_fund_price(chhildNode.get("price").asDouble());
                    stock.setCode(fund.getFundB_id());
                    stock.setMapping("sz");
                    if(!allStock.contains(stock)){
                        allStock.add(stock);
                    }
                    baseFunds.add(fund);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<StockMap> getStocks() {
        return allStock;
    }

    public List<BaseFund> getBaseFunds() {
        return baseFunds;
    }

    public static void main(String[] args) {
        new Builder().build();
    }

}
