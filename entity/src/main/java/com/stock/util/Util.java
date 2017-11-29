package com.stock.util;

import com.stock.bean.Account;
import com.stock.bean.Keys;
import com.stock.bean.OrderInfo;
import com.stock.bean.OwnStock;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by kerno on 16-1-6.
 */
public class Util {
    static Logger logger = LoggerFactory.getLogger(Util.class);
    private static Boolean check=true;
    public static Boolean isLoad = true;
    private static Integer prex = 7;
    private volatile static HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
    static {
        if(Keys.needProxy){
            client.getHostConfiguration().setProxy("cn-proxy.jp.oracle.com",80);
        }
    }

    public static String buy(HttpClient client ,String account, String stock, Double price, Integer num) throws IOException {
        PostMethod method = new PostMethod(Keys.GTJA_ADDORDER_ADDR);

        NameValuePair[] param = getBuyParam(stock,price,num);
        method.setRequestBody(param);
        String response = processResponse(client,method, account);
        logger.info("- Buy - response:"+response);
        logger.warn("- Buy - response:"+response);
        if (processTimeout(account, response)) return Keys.ERROR_ORDER_NO;
        int index = 0;
        if(response.indexOf("该笔合同序号为") > 0){
            index = response.indexOf("该笔合同序号为");
        }else{
            logger.warn("- Buy - can not get orderNO successful..." + response);
            logger.warn("- Buy - gtja_entrust_sno " + param[0].getValue());
            logger.warn("- Buy - stkcode " + param[4].getValue());
            logger.warn("- Buy - price " + param[6].getValue());
            logger.warn("- Buy - qty " + param[7].getValue());
            logger.error("- Buy - can not get orderNO successful..." + response);
            logger.error("- Buy - gtja_entrust_sno " + param[0].getValue());
            logger.error("- Buy - stkcode " + param[4].getValue());
            logger.error("- Buy - price " + param[6].getValue());
            logger.error("- Buy - qty " + param[7].getValue());
            return Keys.ERROR_ORDER_NO;
//            logger.warn("- Buy - try buy process again");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return  buy( client, account ,  stock,  price,  num, ++tryTime);
        }

        return getOrderNoByResponse(response,index);
    }

    private static boolean processTimeout(String account, String response) {
        if(!checkTimeOut(response,account)){
            logger.warn(account + "timeout");
            logger.error(account + "timeout");
            try {
                Sender.send(Keys.needProxy, DB.getAccount(account).getEmail(),Keys.TIME_OUT,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static String autoBuy(HttpClient client ,String account , String stock, Double price, Integer num) throws IOException {
        PostMethod method = new PostMethod(Keys.GTJA_ADDORDER_ADDR);
        NameValuePair[] param = getBuyParam(stock,price,num);
        method.setRequestBody(param);
        String response = processResponse(client,method, account);
        int index = 0;
        if (processTimeout(account, response)) return Keys.ERROR_ORDER_NO;
        logger.info("- Auto Buy - response:"+response);
        logger.warn("- Auto Buy - response:"+response);
        if(response.indexOf("该笔合同序号为") > 0){
            index = response.indexOf("该笔合同序号为");
            logger.warn(" - Auto Buy - can not get orderNO successful..." + response);
            logger.warn(" - Auto Buy - gtja_entrust_sno " + param[0].getValue());
            logger.warn(" - Auto Buy - stkcode " + param[4].getValue());
            logger.warn(" - Auto Buy - price " + param[6].getValue());
            logger.warn(" - Auto Buy - qty " + param[7].getValue());
        }else{
            logger.warn(" - Auto Buy - can not get orderNO successful..." + response);
            logger.warn(" - Auto Buy - gtja_entrust_sno " + param[0].getValue());
            logger.warn(" - Auto Buy - stkcode " + param[4].getValue());
            logger.warn(" - Auto Buy - price " + param[6].getValue());
            logger.warn(" - Auto Buy - qty " + param[7].getValue());
            logger.error(" - Auto Buy - can not get orderNO successful..." + response);
            logger.error(" - Auto Buy - gtja_entrust_sno " + param[0].getValue());
            logger.error(" - Auto Buy - stkcode " + param[4].getValue());
            logger.error(" - Auto Buy - price " + param[6].getValue());
            logger.error(" - Auto Buy - qty " + param[7].getValue());
        }
        return getOrderNoByResponse(response,index);
    }

    public static String autoSell(HttpClient client ,String account, String stock, Double price,Double costPrice, Integer num) throws IOException {
        PostMethod method = new PostMethod(Keys.GTJA_ADDORDER_ADDR);
        NameValuePair[] param = getSellParam(price,costPrice,stock,num);
        method.setRequestBody(param);
        String response = processResponse(client,method, account);
        logger.info("- Auto Sell - response:"+response);
        logger.warn("- Auto Sell - response:"+response);
        if (processTimeout(account, response)) return Keys.ERROR_ORDER_NO;
        int index = 0;
        if(response.indexOf("该笔合同序号为") > 0){
            index = response.indexOf("该笔合同序号为");
        }else{
            logger.warn(" - Auto Sell - can not get orderNO successful..." + response);
            logger.warn("- Auto Sell - gtja_entrust_sno " + param[0].getValue());
            logger.warn("- Auto Sell - costprice " + param[1].getValue());
            logger.warn("- Auto Sell - stkcode " + param[3].getValue());
            logger.warn("- Auto Sell - price " + param[5].getValue());
            logger.warn("- Auto Sell - qty " + param[6].getValue());
            logger.error("- Auto Sell - can not get orderNO successful..." + response);
            logger.error("- Auto Sell - gtja_entrust_sno " + param[0].getValue());
            logger.error("- Auto Sell - costprice " + param[1].getValue());
            logger.error("- Auto Sell - stkcode " + param[3].getValue());
            logger.error("- Auto Sell - price " + param[5].getValue());
            logger.error("- Auto Sell - qty " + param[6].getValue());
        }

        return getOrderNoByResponse(response,index);
    }

    public static String sell(HttpClient client ,String account, Double price, Double costPrice, String stock, Integer num) throws IOException {
        if(Keys.DEBUG){
            return Keys.ORDERNO;
        }
        PostMethod method = new PostMethod(Keys.GTJA_ADDORDER_ADDR);
        NameValuePair[] param = getSellParam(price,costPrice,stock,num);
        method.setRequestBody(param);
        String response = processResponse(client,method,account);
        logger.info("- Sell - response:"+response);
        logger.warn("- Sell - response:"+response);
        if (processTimeout(account, response)) return Keys.ERROR_ORDER_NO;
        int index = 0;
        if(response.indexOf("该笔合同序号为") > 0){
            index = response.indexOf("该笔合同序号为");
        }else{
            logger.warn(" - Sell - can not get orderNO successful..." + response);
            logger.warn(" - Sell - gtja_entrust_sno " + param[0].getValue());
            logger.warn(" - Sell - costprice " + param[1].getValue());
            logger.warn(" - Sell - stkcode " + param[3].getValue());
            logger.warn(" - Sell - price " + param[5].getValue());
            logger.warn(" - Sell - qty " + param[6].getValue());
            logger.error(" - Sell - can not get orderNO successful..." + response);
            logger.error(" - Sell - gtja_entrust_sno " + param[0].getValue());
            logger.error(" - Sell - costprice " + param[1].getValue());
            logger.error(" - Sell - stkcode " + param[3].getValue());
            logger.error(" - Sell - price " + param[5].getValue());
            logger.error(" - Sell - qty " + param[6].getValue());
//            if(tryTime >=3){
            return Keys.ERROR_ORDER_NO;
//            }
//            logger.warn(" - Sell - try sell process again");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return  sell( client,account,  price,  costPrice,  stock,  num,++tryTime);
        }

        return getOrderNoByResponse(response,index);
    }

    public static List<OrderInfo> getTurnOverInfo(HttpClient client,String account,String param,List<OrderInfo> previous){
        GetMethod method = new GetMethod(Keys.GTJA_DEALINFO_TADAY+param);
        List<OrderInfo> list = new ArrayList<>();
        try {
            String response = processResponse(client,method, account);
            if(!checkTimeOut(response,account)){
                logger.error(account + "timeout");
                return null;
            }
            org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
            Elements trs = doc.getElementsByTag("table").get(2).child(0).child(0).child(0).child(3).child(0).children();
            OrderInfo order = null;
            for (int i = 1; i < trs.size() - 1; i++) {
                Elements el = trs.get(i).getElementsByTag("td");
                order = new OrderInfo();
                order.setCode(el.get(2).text());
                order.setOrderNo(el.get(3).text());
                order.setDealType(el.get(4).text());
                order.setPrice(Double.valueOf(el.get(5).text()));
                order.setCount(Double.valueOf(el.get(6).text()));
                order.setMoney(Double.valueOf(el.get(7).text()));
                order.setTime(el.get(8).text());
                list.add(order);
            }
            Boolean pass = true;
            if(previous != null){
                for(OrderInfo info : previous){
                    if(info.getOrderNo().equals(list.get(0).getOrderNo())){
                        pass = false;
                    }
                }
            }
            if(list.size() == 15 && pass){
                String url = trs.get(16).getElementsByTag("td").get(2).getElementsByTag("a").get(1).attr("href");
                url = url.substring(url.indexOf("&"));
                list.addAll(getTurnOverInfo(client,account,url,list));
            }else{

            }
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Double updateAccountCanUseMoney(HttpClient client,String accountId){
        GetMethod method = new GetMethod(Keys.GTJA_OWNINFO);
        List<OwnStock> list = new ArrayList<>();
        try {
            String response = processResponse(client, method, accountId);
            if (!checkTimeOut(response, accountId)) {
                logger.error(accountId + "updateAccountCanUseMoney timeout");
                return null;
            }
            org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
            if (doc.getElementsByTag("table").size() < 3) {
                logger.warn("updateAccountCanUseMoney failed account info ");
                logger.error("updateAccountCanUseMoney failed account info " + response);
                return null;
            }
            String accountMoney = doc.getElementsByTag("table").get(3).getElementsByTag("table").get(2).getElementsByTag("tr").get(2).child(3).text();
            return Double.valueOf(accountMoney);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("updateAccountCanUseMoney",ex);
        }
        return null;
    }

    public static List<OwnStock> getOwnStock(HttpClient client, Account account){
        GetMethod method = new GetMethod(Keys.GTJA_OWNINFO);
        List<OwnStock> list = new ArrayList<>();
        try {
            String response = processResponse(client,method, account.getId());
            if(!checkTimeOut(response,account.getId())){
                logger.error(account + "timeout");
                return list;
            }
            org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
            if(doc.getElementsByTag("table").size() <3 ){
                logger.warn("getOwnStock failed account info " + response);
                logger.error("getOwnStock failed account info " + response);
                return null;
            }
            String accountMoney = doc.getElementsByTag("table").get(3).getElementsByTag("table").get(2).getElementsByTag("tr").get(2).child(3).text();
            account.setCanUseMoney(Double.valueOf(accountMoney));
            Elements trs = doc.getElementsByTag("table").get(2).child(0).child(0).child(0).child(3).child(0).children();
            OwnStock order = null;
            for (int i = 1; i < trs.size() - 1; i++) {
                order = new OwnStock();
                Elements el = trs.get(i).getElementsByTag("td");
                order.setId(el.get(1).text());
                order.setName(el.get(2).text());
                order.setOwnCount(Double.valueOf(el.get(3).text()));
                order.setCanUseCount(Double.valueOf(el.get(4).text()));
//                if(order.getCanUseCount() <=Keys.SELL_BASE_VOLUME){
//                    order.setSellVolume(order.getCanUseCount().intValue());
//                }else{
//                    order.setSellVolume(Double.valueOf((order.getCanUseCount() * Keys.SELL_BASE) / 100).intValue() * 100);
//                }
                order.setCostPrice(Double.valueOf(el.get(7).text()));
                logger.warn("own " + order.getId() +" can use : " + order.getCanUseCount());
                if(order.getCanUseCount() > 0.0){
                    list.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String processResponse(HttpClient client, HttpMethod method, String account) throws IOException {
        int code = client.executeMethod(method);
        StringBuffer temp = new StringBuffer();
        try{
            if(code == 200) {
                InputStream is = method.getResponseBodyAsStream();
                InputStreamReader ir = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(ir);
                String tempLine = br.readLine();
                while (tempLine != null) {
                    temp.append(tempLine);
                    tempLine = br.readLine();
                }
                br.close();
                ir.close();
                is.close();
                if(!checkTimeOut(temp.toString(),account)){
                    logger.warn("account cookie timeout");
                    logger.error("account cookie timeout");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return temp.toString();
    }

    public static String getResponseByUrl(String url){
        try{
            GetMethod method = new GetMethod(url);
            int code = client.executeMethod(method);
            StringBuffer temp = new StringBuffer();
            try{
                if(code == 200) {
                    InputStream is = method.getResponseBodyAsStream();
                    InputStreamReader ir = new InputStreamReader(is,"gbk");
                    BufferedReader br = new BufferedReader(ir);
                    String tempLine = br.readLine();
                    while (tempLine != null) {
                        temp.append(tempLine);
                        tempLine = br.readLine();
                    }
                    br.close();
                    ir.close();
                    is.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                method.releaseConnection();
            }
            return temp.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "";
    }
    private static NameValuePair[] getBuyParam(String stock,Double price,Integer num){
        NameValuePair[] param =  {
                new NameValuePair("gtja_entrust_sno",new java.util.Date().getTime()+""),
                new NameValuePair("stklevel","L"),
                new NameValuePair("tzdate","0"),
                new NameValuePair("market","2"),
                new NameValuePair("stkcode",stock),
                new NameValuePair("radiobutton","B"),
                new NameValuePair("price",price+""),
                new NameValuePair("qty",num+"")
        };
        return param;
    }
    private static NameValuePair[] getSellParam(Double price,Double costPrice,String stock,Integer num){
        NameValuePair[] param = {
                new NameValuePair("gtja_entrust_sno",new java.util.Date().getTime()+""),
                new NameValuePair("costprice",costPrice+""),
                new NameValuePair("saleStatus","0"),
                new NameValuePair("stkcode",stock),
                new NameValuePair("radiobutton","S"),
                new NameValuePair("price",price+""),
                new NameValuePair("qty",num+"")
        };
        return param;
    }
    private static String getOrderNoByResponse(String response,int index){
        String orderNo = "";
        if(index > 0 ){
            index +=+prex;
            orderNo = response.substring(index);
            orderNo = orderNo.substring(0,orderNo.indexOf("\""));
            orderNo = orderNo.replaceAll("\"","");
            logger.warn("order no is : " + orderNo);
            return orderNo;
        }else{
            return Keys.ERROR_ORDER_NO;
        }
    }

    private static Boolean checkTimeOut(String response,String account){
        Boolean pass = true;
        if(response.indexOf("登录超时过期") > 0){
            pass = false;
            if(DB.getAccount(account).getLockAccountVersion() == 0){
                DB.getAccount(account).setStatus(false);
                try {
                    Sender.send(Keys.needProxy, DB.getAccount(account).getEmail(), Keys.TIME_OUT + CacluateUtil.format(new Date()), response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pass;
    }

}
