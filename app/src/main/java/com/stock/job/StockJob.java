package com.stock.job;

import com.stock.bean.BaseFund;
import com.stock.context.SpringContext;
import com.stock.job.util.DateUtil;
import com.stock.program.fund.Builder;
import com.stock.program.fund.util.IgnoreFund;
import com.stock.service.StockService;
import com.stock.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class StockJob {
    Logger logger = LoggerFactory.getLogger(StockJob.class);
    @Autowired
    private SpringContext context;

    // every 8:00am
    @Scheduled(cron="0/20 * *  * * ? ")
    public void syncJSLFund() throws IOException {
        if(!DateUtil.isValidDay() || DB.getAllMJ().size() >0 ){
            return;
        }
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("H30535");
        ignoreList.add("H30344");
        IgnoreFund.setIgnoreBaseFundList(ignoreList);
        Builder builder = new Builder();
        builder.build();
        for(BaseFund fund : builder.getBaseFunds()){
            DB.getAllMJ().put(fund.getBase_fund_id(),fund);
        }
        StockService service = SpringContext.getBean("service.StockService");
        service.syncMapping(builder.getStocks());
    }

    @Scheduled(cron="0 0/10 09-15 * * ? ")
    public void updateStockMapping(){
        if(DB.failedSyncStock.size()>0){
           StockService service = SpringContext.getBean("service.StockService");
           Iterator<String> iterator = DB.failedSyncStock.iterator();
           Set<String> removeSet = new HashSet<>();
           while(iterator.hasNext()){
               String value = iterator.next();
               logger.info("update failed sotck mapping " + value);
               removeSet.add(value);
               service.updateStockMapping(value);
           }
           DB.failedSyncStock.removeAll(removeSet);
        }
    }
    @Scheduled(cron="0/30 * * * * ? ")
    public void updateAllStockMapping(){
            StockService service = SpringContext.getBean("service.StockService");
            Set<String> removeSet = new HashSet<>();
            service.updateAllStockPrice();
    }
}
