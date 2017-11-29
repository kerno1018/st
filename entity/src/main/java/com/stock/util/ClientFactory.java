package com.stock.util;

import com.stock.bean.Keys;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 2016/1/26.
 */
public class ClientFactory {

    public static HttpClient getClient(String cookie){
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
        headers.add(new Header("Accept-Encoding", "gzip, deflate"));
        headers.add(new Header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4,sq;q=0.2,en-US;q=0.2"));
        headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        headers.add(new Header("Cache-Control", "max-age=0"));
        headers.add(new Header("Connection", "keep-alive"));
        headers.add(new Header("Content-Type", "application/x-www-form-urlencoded"));
        headers.add(new Header("Host", "trade.gtja.com"));
        headers.add(new Header("Cookie",cookie));
        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        if(Keys.needProxy){
            httpClient.getHostConfiguration().setProxy("cn-proxy.jp.oracle.com",80);
        }
        return httpClient;
    }

}
