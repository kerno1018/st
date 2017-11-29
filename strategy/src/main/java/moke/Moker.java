package moke;


import com.stock.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 2016/2/4.
 */
public class Moker {

    public static List<OrderInfo> getOrderInfo(){

        List<OrderInfo> list = new ArrayList<>();

        OrderInfo info = new OrderInfo();
        info.setMoney(100.0);
        info.setPrice(1.2);
        info.setOrderNo("LF123456");
        info.setCount(1000.0);
        info.setCode("123456");
        list.add(info);
        info = new OrderInfo();
        info.setMoney(1000.0);
        info.setPrice(1.2);
        info.setOrderNo("LF654321");
        info.setCount(200.0);
        info.setCode("654321");

        list.add(info);
//        info = new OrderInfo();
//        info.setMoney(1000.0);
//        info.setPrice(1.2);
//        info.setOrderNo("LF654321");
//        info.setCount(1000.0);
//        info.setCode("654321");
//        list.add(info);
        return list;
    }


}
