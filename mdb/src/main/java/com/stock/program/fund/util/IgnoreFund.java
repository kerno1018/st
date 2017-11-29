package com.stock.program.fund.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 2016/4/17.
 */
public abstract class IgnoreFund {
    String IGNORE_UPDATE_BASE_FUND = "H30535,H30344";
    private static List<String> ignore = new ArrayList<>();
    public static List<String> getIgnoreFund(){
        return ignore;
    }
    public static void setIgnoreBaseFundList(List<String> ignoreList){
        ignore = ignoreList;
    }

}
