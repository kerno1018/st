//package hibernate;
//
//import com.stock.bean.Keys;
//import com.stock.entity.StockMap;
//import com.stock.util.DB;
//import Dao;
//import com.stock.util.Util;
//
//import java.util.List;
//
///**
// * Created by kerno on 2016/4/12.
// */
//public class StockInfoAutoUpdate implements Runnable{
//    Dao dao;
//    public StockInfoAutoUpdate(Dao dao){
//        this.dao = dao;
//    }
//    @Override
//    public void run(){
////        Map<String,String> mapping = ;
//        if(DB.stockMapping.size() == 0){
//            String hql = "from StockMap";
//            List<StockMap> list = dao.getSession().createQuery(hql).list();
//            for(StockMap map : list){
//                DB.stockMapping.put(map.getCode(),map.getMapping());
//            }
//        }
//
//        while (true){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            StringBuilder builder = new StringBuilder();
//            int i = 0;
//            for(String key : DB.stockMapping.keySet()){
//                i++;
//                builder.append(DB.stockMapping.get(key)+key).append(",");
//                if(i%20 == 0){
//                    String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
//                    DB.updateStockInfo(responseText);
//                    builder = new StringBuilder();
//                }
//            }
//            if(builder.length() > 0){
//                System.out.println(("curl and update stock info url " + builder.toString()));
//                String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
//                DB.updateStockInfo(responseText);
//            }
//        }
//    }
//}
