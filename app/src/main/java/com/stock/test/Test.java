package com.stock.test;

import com.stock.bean.Account;
import com.stock.bean.OwnStock;
import com.stock.job.StockJob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kerno on 2016/2/24.
 */
public class Test {
   static Map<String,List<Account>> map = new HashMap<>();

@org.junit.Test
    public void main() throws InterruptedException {
        OwnStock stock = new OwnStock();
        stock.setCanUseCount(100.0);
        Map<String,OwnStock> stocks = new HashMap<>();
        stocks.put("1",stock);
        Account account = new Account();
        account.setOwnStock(stocks);
        List<Account> list = new ArrayList<>();
        list.add(account);

        OwnStock s = account.getOwnStock().get("1");
        double[] value = {10.0,11.0,12.0,13.0,14.0,15.0,16.0,17.0,18.0,19.0,20.0};
        for(int i=0;i<100;i++){
            new Thread(new T(s)).start();
        }
    while (true){
//        System.out.println(account.getOwnStock().get("1").getCanUseCount());
//        Thread.sleep(10);
    }
    }
class T implements Runnable{
    private OwnStock ownStock;
    private Double value;
    public T (OwnStock ownStock){
        this.ownStock = ownStock;
    }
    @Override
    public void run() {
        if(ownStock.getCanUseCount()>0.0){
            ownStock.setCanUseCount(ownStock.getCanUseCount()-10);
        }else{
            System.out.println("error");
        }
    }
}

    @org.junit.Test
    public void tt() throws IOException {
        new StockJob().syncJSLFund();
    }

}
