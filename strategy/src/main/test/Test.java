import com.stock.bean.*;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.util.Sender;
import org.junit.Before;
import sell.BCommand;
import util.compator.BaseFundCompare;

import java.io.*;
import java.util.*;
import java.util.Date;

/**
 * Created by kerno on 12/31/2015.
 */
public class Test {
    private Account account;
    private BaseFund adviseFund;
    private BaseFund ownFund;
//    @Before
//    public void buildABAccount(){
//        account = new Account();
//        account.setId("123");
//        Map<String , OwnStock> ownStockMap = new HashMap<>();
//        OwnStock stock = new OwnStock();
//        stock.setCanUseCount(1000.0);
//        stock.setId("123456");
//        ownStockMap.put(stock.getId(),stock);
//        stock = new OwnStock();
//        stock.setId("654321");
//        ownStockMap.put(stock.getId(),stock);
//        stock.setCanUseCount(1000.0);
//        account.setOwnStock(ownStockMap);
//        adviseFund = new BaseFund();
//        adviseFund.setAllPresistmax();
//        ownFund = new BaseFund();
//        ownFund.setFundA_id("123456");
//        ownFund.setFundB_id("654321");
//        ownFund.setAllPresistmax();
//        Stock s = new Stock();
//        s.setBuyFivePrice(0.8);
//        s.setSellFivePrice(0.8);
////        s.set
//        adviseFund.setMarketA(s);
//        adviseFund.setMarketB(s);
//        ownFund.setMarketA(s);
//        ownFund.setMarketB(s);
//    }

//    @org.junit.Test
//    public void testCompare(){
//
//        List<BaseFund> list = new LinkedList<>();
//        for(int i=0;i<10;i++){
//            BaseFund fund = new BaseFund();
//            if(i%2==0){
//                fund.setCurrentSellFivePremium(new Random().nextDouble());
//
//
//            }else{
//                fund.setCurrentSellFivePremium(-new Random().nextDouble());
//            }
//            list.add(fund);
//        }
//
//        Collections.sort(list,new BaseFundCompare());
//
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i).getSellPremium());
//        }
//
//
//    }
@Before
public void buildBAccount(){
    account = new Account();
    account.setId("123");
    account.setCanUseMoney(1000.0);
    Map<String , OwnStock> ownStockMap = new HashMap<>();
    OwnStock stock = new OwnStock();
    stock.setCanUseCount(1000.0);
    stock.setId("123456");
    ownStockMap.put(stock.getId(),stock);
    stock = new OwnStock();
    stock.setId("654321");
    ownStockMap.put(stock.getId(),stock);
    stock.setCanUseCount(1000.0);
    account.setOwnStock(ownStockMap);
    adviseFund = new BaseFund();
    adviseFund.setAllPresistmax();
    ownFund = new BaseFund();
    ownFund.setFundB_id("123456");
//    ownFund.setFundB_id("654321");
    ownFund.setAllPresistmax();
    Stock s = new Stock();
    s.setBuyFivePrice(0.8);
    s.setSellFivePrice(0.8);
    s.setSellTwoPrice(0.5);
    s.setSellOnePrice(0.5);
    s.setBuyOnePrice(0.5);

//        s.set
    adviseFund.setMarketA(s);
    adviseFund.setMarketB(s);
    adviseFund.setFundB_id("654321");
    ownFund.setMarketA(s);
    ownFund.setMarketB(s);
}

    @org.junit.Test
    public void sell() throws InterruptedException {
        Keys.DEBUG = true;
        Keys.needProxy=false;
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
//        new Seller(adviseFund,ownFund,account).checkSuccessOrNot(false,false,list,500,account,false,1000,2000);
        new Thread(new BCommand(adviseFund, ownFund, account, 1000.0)).start();
//        new Thread(new EagerBSeller(adviseFund, ownFund, account, 1000.0)).start();

        while (DB.dealQueue.size()!=2){
            Thread.sleep(1000);
        }
        System.out.println(DB.dealQueue.poll().getYj());
        System.out.println(DB.dealQueue.poll().getYj());
    }

    @org.junit.Test
    public void testGBKFile() throws Exception {

        List<BaseFund> funds = new ArrayList<>();
        BaseFund fund = new BaseFund();
        fund.setCurrentSellOnePremium(null);
        funds.add(fund);
        fund = new BaseFund();
        fund.setCurrentSellOnePremium(0.1);
        funds.add(fund);
        fund = new BaseFund();
        fund.setCurrentSellOnePremium(-0.2);
        funds.add(fund);
        fund = new BaseFund();
        fund.setCurrentSellOnePremium(0.3);
        funds.add(fund);


        Collections.sort(funds,new BaseFundCompare());
        System.out.println(funds.get(0).getCurrentSellOnePremium());

        System.out.println(-1 < -1);
    }




}
