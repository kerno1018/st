package com.stock.job;

import com.stock.bean.Group;
import com.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.stock.util.DB;

import java.io.IOException;

/**
 * Created by kerno on 1/14/2016.
 */
//@Component
public class PositionJob {
    Logger logger = LoggerFactory.getLogger(PositionJob.class);
    @Autowired
    private StockService service;
//    @Scheduled(cron="0 0/1 09-15  * * ? ")
    public void syncPosition() throws IOException {
        Group roll = DB.getStrategy(1);
        if(roll != null && DB.getAllMJ().size() > 0){
            logger.info("sync position........");
            service.syncPosition(roll.getProp1());
        }
//        roll = DB.getStrategy(2);
//        if(roll != null && DB.getAllEtf().size()>0){
//            service.syncPosition(roll.getProp1());
//        }
    }

}
