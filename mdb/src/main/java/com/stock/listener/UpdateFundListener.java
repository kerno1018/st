package com.stock.listener;

import com.stock.bean.Keys;
import com.stock.program.fund.timer.FundUpdateTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by kerno on 2016/4/17.
 */
public class UpdateFundListener implements  ServletContextListener {
    FundUpdateTimer updateTimer = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        updateTimer = new FundUpdateTimer();
        updateTimer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Keys.FORCE_STOP = true;
        updateTimer.stop();
    }
}
