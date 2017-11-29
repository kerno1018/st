package com.stock.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kerno on 16-4-10.
 */
public class StockCodeUtil {
    HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

    public List<String> buildStockCodeList() throws IOException {
        String url="http://quote.eastmoney.com/stocklist.html";
        GetMethod httpGet = new GetMethod(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpClient.executeMethod(httpGet);
        String str =  httpGet.getResponseBodyAsString();
        org.jsoup.nodes.Document doc = Jsoup.parse(str, "UTF-8");
        Elements strs =doc.getElementsByAttributeValue("class","quotebody").get(0).getElementsByTag("li");
        List<String> result = new LinkedList<>();
        String rex="[()]+";
        for(Element element: strs){
            String value=new String(element.text().trim().getBytes("iso-8859-1"),"gbk");
            String[] code=value.split(rex);
            result.add(code[1]);
        }
        return result;
    }





}
