package com.stock.job;


import com.stock.entity.AutoOrder;
import com.stock.service.AutoOrderService;
import com.stock.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import program.AutoBuyCommand;

import com.stock.util.CacluateUtil;

import java.util.*;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class AutoOrderJob {
    private Logger logger = LoggerFactory.getLogger(AutoOrderJob.class);
    @Autowired
    private AutoOrderService service;


    @Scheduled(cron="0 0/10 08-15 * * ? ")
    public void prepare(){
        if(DB.autoOrders.size()==0){
            service.prepareData();
        }
    }

    @Scheduled(cron="0/1 * 09-15 * * ? ")
    public void autoBuy(){
        if(DB.autoOrders.size()>0){
            List<Integer> removeIds = new LinkedList<>();
            for(AutoOrder order : DB.autoOrders.values()){
                if(new Date().getTime()/1000 == (CacluateUtil.convertToDate(order.getOperateTime())).getTime()/1000){
                    if(DB.getAccount(order.getUserId().toString()) != null){
                        new Thread(new AutoBuyCommand(DB.getAccount(order.getUserId().toString()),order)).start();
                        removeIds.add(order.getId());
                    }else{
                        logger.info(" - Auto Order - account "+order.getUserId()+" haven't login can not do auto buy process");
                    }
                }
            }
            StringBuilder builder = new StringBuilder();
            for(Integer key : removeIds){
                builder.append(key).append(",");
                DB.autoOrders.remove(key);
            }
            if(builder.length() > 0){
                service.updateRecordToDone(builder.deleteCharAt(builder.length()-1).toString());
            }
        }
    }
}
