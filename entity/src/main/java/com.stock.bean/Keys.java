package com.stock.bean;

import java.util.Date;

/**
 * Created by kerno on 1/8/2016.
 */
public class Keys {
     public static Boolean PICK_MONEY_UPDATE_DONE = false;
     public static Boolean FORCE_STOP = false;
     public static boolean AUTO_LOGIN_TYPE = false;
     public static  String RANDOM_MJ = "";
     public static Boolean needProxy = false;
     public static  String BUY = "BUY";
     public static  String SELL = "SELL";
     public static Boolean DEBUG= false;
     public static Boolean SHOW_LOG=true;
     public static Double POSITION = 0.95;
     public static Double FORBID_SELL_THRESHOLD = 100.0;
     public static Double FORBID_ROLLING_LIMIT = 5000.0;
     public static  Integer STRATEGE_ZQ_ETF_ROLL_TYPE = 1;
     public static  Integer STRATEGE_JG_ETF_ROLL_TYPE = 2;
     public static String STRATEGE_ZQ_ETF_ROLL_URL_MJ ="https://www.jisilu.cn/data/sfnew/fundm_list/?___t="+new Date().getTime();
     public static String STRATEGE_ETF_ROLL_URL="https://www.jisilu.cn/jisiludata/etf.php?___t="+new Date().getTime();
     public static  String URL_STOCKK = "http://hq.sinajs.cn/list=";
     public static String GTJA_ADDORDER_ADDR = "https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=entrustBusinessOut";
     public static String GTJA_DEALINFO_TADAY = "https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=searchDealDetailToday";
     public static  String GTJA_DEAL_ENTREST="https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=searchEntrustDetailToday";
     public static  String GTJA_OWNINFO = "https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=searchStackDetail";
     public static String DEAL_TYPE = "普通成交";
     public static String SZ_URL = "http://hq.sinajs.cn/list=sz399001";
     public static Double CONDITION_PREMINUM = 0.22;
     public static Double CONDITION_PREMINUM_ENERGY = 0.5;
     public static  String ORDERNO = "123";
     public static Double SELL_BASE = 0.2;
     public static Double SELL_BASE_VOLUME = 3000.0;
     public static Double SELL_THRESHOLD = 10000.0;
     public static String ERROR_ORDER_NO = "NON-ORDER";
     public static Double EAGER_PREX = 0.001;
     public static String TIME_OUT = "Account Session Timeout";
     public static Integer STRATEGY_TYPE=5;
     public static Integer CHECK_ORDER_NUM=1;
     public static String SUCCESS_INFO="{\"success\":true}";
}
