package com.stock.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.bean.Etf;
import com.stock.bean.Group;
import com.stock.bean.GroupType;
import com.stock.bean.Keys;
import com.stock.context.SpringContext;
import com.stock.entity.StockMap;
import com.stock.service.StockService;
import com.stock.util.DB;
import com.stock.util.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kerno on 1/14/2016.
 */
//@Component
public class HSZSRolling {

//    @Autowired
    private SpringContext context;
//    @Scheduled(cron="0/10 * *  * * ? ")
    public void syncETF() throws IOException {
        if(DB.getAllEtf().size() > 0){
            return;
        }
        String source = Util.getResponseByUrl(Keys.STRATEGE_ETF_ROLL_URL);
        JsonNode node = new ObjectMapper().readTree(source);
        List<StockMap> result = new LinkedList<>();
        StockMap stock = null;
        for(JsonNode item : node.path("rows")) {
            if (item != null) {
                JsonNode chhildNode = item.path("cell");
                Etf fund = new Etf();
                stock = new StockMap();
                fund.setIndex_id(chhildNode.get("index_id").textValue());
                fund.setId(chhildNode.get("fund_id").textValue());
                fund.setPrice(Double.valueOf(chhildNode.get("fund_nav").textValue()));
                if("159920".equals(fund.getId())){
                    fund.setIndex_id("HSI");
                }
                stock.setCode(fund.getIndex_id());
                stock.setMapping("sz");
                try {
                    Integer.parseInt(fund.getIndex_id());
                } catch (Exception ex) {
                    stock.setMapping("hk");
                }
                result.add(stock);
                stock = new StockMap();
                stock.setCode(fund.getId());
                stock.setMapping("sz");
                result.add(stock);
                DB.getAllEtf().put(fund.getId(),fund);
            }
        }
        StockService service = context.getBean("service.StockService");
        service.syncMapping(result);
    }

//    @Scheduled(cron="0/3 * *  * * ? ")
    public void calculate() throws InterruptedException {
        if(DB.getAllStrategy().get(2) == null){
            // strategy
            GroupType type = new GroupType();
            type.setId(2);
            type.setName("HSZSRolling");
            type.setDesc("溢价5个点为处发条件");
            // 所有需要轮动的etf母基
            Group group = new Group();
            group.setProp1("164705,159920");
            group.setType(type);
            System.out.println("strategy2.......");
            DB.addStrategy(group);
        }
//
//        BaseFund fund = DB.getAllMJ().get("164705");
//        Etf etf = DB.getAllEtf().get("159920");
//        if(fund != null && etf != null && fund.getPremium() != null && etf.getPremium() != null){
//            if(Math.abs(fund.getPremium() - etf.getPremium()) > 0.2){
//                System.out.println(fund.getPremium() +":fund-----etf:" + etf.getPremium());
//            }
//        }


//        if(fund.getPremium() > 0.3){
//
//            for(Account account : DB.getAllAccount().values()){
//
//                new Thread(new HSZSSeller(fund,account)).start();
//
//
//            }
//
//
//        }
    }

}
