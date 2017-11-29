package com.stock.login;

import com.stock.bean.Account;
import com.stock.bean.Keys;
import com.stock.bean.OwnStock;
import com.stock.entity.User;
import com.stock.service.UserService;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.util.Sender;
import com.stock.util.Util;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * Created by kerno on 16-4-13.
 */
public class AutoLogin implements Runnable{
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AutoLogin.class);
    private HttpClient httpClient = null;
    private Tesseract instance = null;
    private Integer tryTime = 1;
    private User user;
    private UserService service;
    public AutoLogin(UserService service, User user){
        this.service = service;
        this.user = user;
    }

    @Override
    public void run(){
        String url = "https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=preLogin";
        httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
        headers.add(new Header("Accept-Encoding", "gzip, deflate"));
        headers.add(new Header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4,sq;q=0.2,en-US;q=0.2"));
        headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        headers.add(new Header("Cache-Control", "max-age=0"));
        headers.add(new Header("Connection", "keep-alive"));
        headers.add(new Header("Content-Type", "application/x-www-form-urlencoded"));
        headers.add(new Header("Host", "trade.gtja.com"));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        GetMethod get = new GetMethod(url);
        try {
            httpClient.executeMethod(get);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            get.releaseConnection();
        }
        instance = new Tesseract();
        instance.setDatapath(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"/tessdata");
        instance.setTessVariable("tessedit_char_whitelist","ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        try {
            login();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getAppendCode() throws IOException {
        GetMethod get = new GetMethod("https://trade.gtja.com/webtrade/commons/verifyCodeImage.jsp?ran="+Math.random());
        httpClient.executeMethod(get);
        BufferedImage img = ImageIO.read(get.getResponseBodyAsStream());
        get.releaseConnection();
        BufferedImage cleanImage = ClearImageHelper.cleanImage(img);
        try {
            String result = instance.doOCR(cleanImage);
            if(result.trim().length()<4){
                Thread.sleep(2000);
                return getAppendCode();
            }
            return result.replace("\n","").toLowerCase();
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        showCookie();
        return null;
    }

    public String reLogin() throws IOException {
        String appendCode = getAppendCode();
        GetMethod method = new GetMethod("https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=getReserveMsg&orgid=4211&fundtype=Z&inputid="+user.getLoginAccount()+"&ram="+Math.random()+"&_=");
        httpClient.executeMethod(method);
        method.releaseConnection();
        String loginurl="https://trade.gtja.com/webtrade/trade/webTradeAction.do";
//        Scanner sc = new Scanner(System.in);
//        String str = sc.next();
        NameValuePair[] param = {
                new NameValuePair( "method", "login"),
                new NameValuePair( "uid",user.getLoginPassword()  ),
                new NameValuePair( "pwdtype",""  ),
                new NameValuePair( "hardInfo",""  ),
                new NameValuePair( "logintype","common"  ),
                new NameValuePair( "flowno",""  ),
                new NameValuePair( "usbkeySn",""  ),
                new NameValuePair( "usbkeyData",""  ),
                new NameValuePair( "mac",""  ),
                new NameValuePair( "gtja_dating_login_type","0"  ),
                new NameValuePair( "availHeight","448"  ),
                new NameValuePair( "YYBFW","10"  ),
                new NameValuePair( "BranchCode","4211"  ),
                new NameValuePair( "BranchName","湖北宜昌四新路证券营业部"  ),
                new NameValuePair( "Page",""  ),
                new NameValuePair( "selectBranchCode","7001"  ),
                new NameValuePair( "countType","Z"  ),
                new NameValuePair( "inputid",user.getLoginAccount()  ),
                new NameValuePair( "trdpwd",new String(Base64.getEncoder().encode(user.getLoginPassword().getBytes()))),
                new NameValuePair( "AppendCode",appendCode  )
        };
        PostMethod post = new PostMethod(loginurl);
        post.setRequestBody(param);
        httpClient.executeMethod(post);
        String result = post.getResponseBodyAsString();
        post.releaseConnection();
        return result;
    }

    public void login() throws IOException, TesseractException, InterruptedException {
        if(tryTime > 20){
            logger.warn("already try 10+ times will skip ....");
            try {
                Sender.send(Keys.needProxy,user.getEmail(),"You account login failed,plz check in.","Today Auto login is failed" + CacluateUtil.format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String result = reLogin();
        logger.warn("try " + tryTime +" times..." + result);
        if(result.indexOf("客户密码错")>0){
            System.out.println("客户密码错");
            return;
        }
        if(result.indexOf("验证码错误")>0){
            tryTime++;
            Thread.sleep(new Random().nextInt(6)*2000);
            login();
        }else{
            logger.info("auto login successful...");
            user.getAccount().setCookie(httpClient.getState().getCookies()[0].toString()+"; MyBranchCodeList=4211; countType=Z; BranchName=%u6E56%u5317%u5B9C%u660C%u56DB%u65B0%u8DEF%u8BC1%u5238%u8425%u4E1A%u90E8");
            service.saveOrUpdateUser(user);
        }
    }
}
