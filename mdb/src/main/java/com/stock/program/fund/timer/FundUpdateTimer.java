package com.stock.program.fund.timer;

import com.stock.program.fund.Update;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kerno on 2016/4/17.
 */
public class FundUpdateTimer {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Update.syncFundInfo();
            }
        };
        scheduler.scheduleWithFixedDelay(task,1,1, TimeUnit.SECONDS);
    }

    public void stop(){
        scheduler.shutdown();
    }

}
