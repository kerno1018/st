package com.stock.test;

import com.stock.bean.Keys;
import com.stock.util.CacluateUtil;
import com.stock.util.Sender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.stock.service.StockService;

import java.util.Date;

/**
 * Created by kerno on 16-4-11.
 */
public class StockHistoryUpdateTest implements Runnable{

    private StockService service;
    public StockHistoryUpdateTest(StockService service){
        this.service = service;
    }

    public void stockValueUpdate(){
        try{
            service.deleteTodayStockValue();
            service.updateStockValue();
        }catch (Exception ex){
            try {
                Sender.send(Keys.needProxy,"418697994@qq.com","Update StockValue failed.",ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stockHistoryUpdate(){
        try{
            service.deleteTodayStockHistory();
            service.addTodayStockHistory(service.getAllStockMap());
        }catch (Exception ex){
            ex.printStackTrace();
            try {
                Sender.send(Keys.needProxy,"418697994@qq.com","Update history failed.",ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        stockHistoryUpdate();
        stockValueUpdate();
    }

}
