package com.stock.program.fund;

import com.stock.bean.Keys;
import com.stock.util.DB;
import com.stock.util.Util;

/**
 * Created by kerno on 2016/4/17.
 */
public class Update {

    public static void syncFundInfo(){

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for(String key : DB.stockMapping.keySet()){
            i++;
            builder.append(DB.stockMapping.get(key)+key).append(",");
            if(i%300 == 0){
                String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
                DB.updateStockInfo(responseText);
                builder = new StringBuilder();
            }
        }
        if(builder.length() > 0){
            String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
            DB.updateStockInfo(responseText);
        }
    }
}
