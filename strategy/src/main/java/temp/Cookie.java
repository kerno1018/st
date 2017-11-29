//package temp;
//
///**
// * Created by kerno on 1/23/2016.
// */
//public class Cookie {
//    private String webtrade_all_yyb             ;
//    private String webtrade_last_zhlx="Z"           ;
//    private String webtrade_last_yyb            ;
//    private String webtrade_last_Account        ;
//    private String webtrade_save_Account ="true"       ;
//    private String webtrade_last_verifytype  ="0"   ;
//    private String webtrade_last_logintype ="1"     ;
//    private String JSESSIONID =""                  ;
//    private String zxg_cookie                   ;
//
//    public String getWebtrade_all_yyb() {
//        return webtrade_all_yyb;
//    }
//
//    public void setWebtrade_all_yyb(String webtrade_all_yyb) {
//        this.webtrade_all_yyb = webtrade_all_yyb;
//    }
//
//    public String getWebtrade_last_zhlx() {
//        return webtrade_last_zhlx;
//    }
//
//    public void setWebtrade_last_zhlx(String webtrade_last_zhlx) {
//        this.webtrade_last_zhlx = webtrade_last_zhlx;
//    }
//
//    public String getWebtrade_last_yyb() {
//        return YHEntry.entry.entry(webtrade_all_yyb);
//    }
//
//    public void setWebtrade_last_yyb(String webtrade_last_yyb) {
//        this.webtrade_last_yyb = webtrade_last_yyb;
//    }
//
//    public String getWebtrade_last_Account() {
//        return YHEntry.entry.entry(webtrade_last_Account);
//    }
//
//    public void setWebtrade_last_Account(String webtrade_last_Account) {
//        this.webtrade_last_Account = webtrade_last_Account;
//    }
//
//    public String getWebtrade_save_Account() {
//        return webtrade_save_Account;
//    }
//
//    public void setWebtrade_save_Account(String webtrade_save_Account) {
//        this.webtrade_save_Account = webtrade_save_Account;
//    }
//
//    public String getWebtrade_last_verifytype() {
//        return webtrade_last_verifytype;
//    }
//
//    public void setWebtrade_last_verifytype(String webtrade_last_verifytype) {
//        this.webtrade_last_verifytype = webtrade_last_verifytype;
//    }
//
//    public String getWebtrade_last_logintype() {
//        return webtrade_last_logintype;
//    }
//
//    public void setWebtrade_last_logintype(String webtrade_last_logintype) {
//        this.webtrade_last_logintype = webtrade_last_logintype;
//    }
//
//    public String getJSESSIONID() {
//        return "JSESSIONID="+JSESSIONID+"; ";
//    }
//
//    public void setJSESSIONID(String JSESSIONID) {
//        this.JSESSIONID = JSESSIONID;
//    }
//
//    public String getZxg_cookie() {
//        return zxg_cookie;
//    }
//
//    public void setZxg_cookie(String zxg_cookie) {
//        this.zxg_cookie = zxg_cookie;
//    }
//
//    public String getOrderCookie(){
////        JSESSIONID=sHjCWjrHh70ZJRXyvmpTnBSbCP39scvsTyKpLsjKv2MSfCQDYn1z!-635995521!-310693852; 10100074075=; zxg_cookie=; authorid=FE1F97F6-A5DF-4327-AA85-60CE35BDFBAA
//        return getJSESSIONID()+webtrade_last_Account+"=;zxg_cookie=;"+"authorid=FE1F97F6-A5DF-4327-AA85-60CE35BDFBAA";
//    }
//
//
//    @Override
//    public String toString() {
//        String str = "webtrade_all_yyb="+webtrade_all_yyb;
//        StringBuilder result = new StringBuilder();
//        result.append("webtrade_all_yyb=").append(getWebtrade_all_yyb()).append("; ");
//        result.append("webtrade_last_zhlx=").append(getWebtrade_last_zhlx()).append("; ");
//        result.append("webtrade_last_yyb=").append(getWebtrade_last_yyb()    ).append("; ");
//        result.append("webtrade_last_Account=").append(getWebtrade_last_Account()).append("; ");
//        result.append("webtrade_last_verifytype=").append(getWebtrade_last_verifytype()).append("; ");
//        result.append("webtrade_last_logintype=").append(getWebtrade_last_logintype()).append("; ");
//        result.append("webtrade_save_Account=true").append("; ");
//        result.append(getJSESSIONID());
//        result.append(webtrade_last_Account).append("=").append("; ");
//        result.append("zxg_cookie=").append(";");
////        result.append("authorid=14C9FDFD-7662-4510-900D-091150AA9896");
//        return result.toString();
//    }
//}
