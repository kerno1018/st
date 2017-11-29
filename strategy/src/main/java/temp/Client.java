//package temp;
//
//import com.mysql.jdbc.StringUtils;
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.jsoup.Jsoup;
//import org.junit.Before;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by kerno on 1/23/2016.
// */
//public class Client {
//    HttpClient httpClient = null;
//    Cookie cookie=null;
//    String verifyCode="";
//    @Before
//    public synchronized void init(){
//        if(cookie != null){
//            return;
//        }
//        cookie = getCookie();
//        httpClient = new HttpClient();
//        List<Header> headers = new ArrayList<Header>();
//        headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
//        headers.add(new Header("X-Requested-With", "XMLHttpRequest"));
//        headers.add(new Header("Referer", "https://www.chinastock.com.cn/trade/webtrade/login.jsp"));
//        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
//        // getjsession
//        cookie.setJSESSIONID("7wLGWjyBhCTghg2BSKrw419vJnXD81QHZpppFv6QgtzKmc9J161p!-635995521!-310693852;");
//    }
//
//
//
//
//
//    public void refresh(){
//        if(cookie == null){
//            init();
//        }
////        https://www.chinastock.com.cn/trade/AjaxServlet?
//        PostMethod method = new PostMethod("https://www.chinastock.com.cn/trade/AjaxServlet");
//        method.addRequestHeader(new Header("Cookie",cookie.getOrderCookie()));
//        method.setDoAuthentication(true);
//        method.addParameter("ajaxFlag","gpmrcx");
//        method.addParameter("stockCode","150234");
//        try {
//            httpClient.executeMethod(method);
//            System.out.println(method.getResponseBodyAsString());
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            method.releaseConnection();
//        }
//    }
//
//    public void heart(){
//        if(cookie == null){
//            init();
//        }
//        PostMethod method = new PostMethod("https://www.chinastock.com.cn/trade/AjaxServlet?ajaxFlag=heartbeat");
//        method.addRequestHeader(new Header("Cookie",cookie.getOrderCookie()));
//        method.setDoAuthentication(true);
//        method.addParameter("ftype","bsn");
//        try {
//            int statusCode = httpClient.executeMethod(method);
//            if(method.getResponseHeader("Set-Cookie") != null){
//                cookie.setJSESSIONID(method.getResponseHeader("Set-Cookie").getValue().split(";")[0]);
//            }
//            System.out.println(method.getResponseBodyAsString());
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            method.releaseConnection();
//        }
//    }
//
//    private Cookie getCookie(){
//
//        Cookie cookie = new Cookie();
//        cookie.setWebtrade_all_yyb("001004001010101");
//        cookie.setWebtrade_last_Account("010100074075");
//        return cookie;
//    }
//
//
//    public String getYHSession() {
//        GetMethod method = new GetMethod("https://www.chinastock.com.cn/trade/webtrade/login.jsp");
//        method.setDoAuthentication(true);
//        String cookie="";
//        try {
//            httpClient.executeMethod(method);
//            String value = method.getResponseHeader("Set-Cookie").getValue();
//            if(!StringUtils.isNullOrEmpty(value)){
//                cookie = value.split(";")[0];
//                this.cookie.setJSESSIONID(method.getResponseHeader("Set-Cookie").getValue().split(";")[0]);
//            }
//            org.jsoup.nodes.Document doc = Jsoup.parse(method.getResponseBodyAsString(), "UTF-8");
//            String order = doc.getElementById("captchaImage").attr("src");
//            order = order.substring(order.length()-4);
//            verifyCode = order;
//        }catch(Exception ex){
//
//        }finally {
//            method.releaseConnection();
//        }
//        return cookie;
//    }
//}
