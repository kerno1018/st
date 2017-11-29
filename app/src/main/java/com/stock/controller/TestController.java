package com.stock.controller;

import com.stock.util.Util;
import org.apache.commons.httpclient.HttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by kerno on 16-4-22.
 */
@RestController
public class TestController {
    @RequestMapping("/test/sell")
    public void testSell(){
        try {
            Util.sell(new HttpClient(),"12345",1.1,1.0,"175831",100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/test/buy")
    public void testBuy(){
        try {
            Util.buy(new HttpClient(),"12345","483910",0.4,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
