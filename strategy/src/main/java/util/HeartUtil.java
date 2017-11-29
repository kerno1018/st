package util;

import com.stock.bean.Account;
import com.stock.bean.Keys;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;

/**
 * Created by kerno on 2016/1/26.
 */
public class HeartUtil {
    private  static Logger logger = LoggerFactory.getLogger(HeartUtil.class);
    public static void heart(Account account){
        String[] s = new String[3];
        s[0]= Keys.GTJA_OWNINFO;
        s[1] = "https://trade.gtja.com/webtrade/trade/webTradeAction.do?method=searchEntrustDetailToday";
        s[2] = Keys.GTJA_DEALINFO_TADAY;
        logger.info("keep heart " + new Date());
            try {
                GetMethod method = new GetMethod(s[new Random().nextInt(3)]);
                int code = account.getClient().executeMethod(method);
                if(code == 200){
                    InputStream is =method.getResponseBodyAsStream();
                    InputStreamReader ir = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader( ir );
                    String tempLine = br.readLine();
                    StringBuffer temp = new StringBuffer();
                    String crlf=System.getProperty("line.separator");
                    while (tempLine != null)
                    {
                        temp.append(tempLine);
                        temp.append(crlf);
                        tempLine = br.readLine();
                    }
                    br.close();
                    ir.close();
                    is.close();
                    method.releaseConnection();
                    if(temp.toString().indexOf("登录超时过期")>0){
                        logger.error("session timeout");
                        account.setStatus(false);
                        return;
                    }
                    org.jsoup.nodes.Document doc = Jsoup.parse(temp.toString(), "UTF-8");
                    if(doc.getElementsByTag("table") != null && doc.getElementsByTag("table").size()<=13){
                        logger.info("error response contents "+temp.toString());
                        return;
                    }
                }else{
                    logger.info("error response code"+ code);
                    logger.error("error response code"+ code);
                }
            } catch (IOException e) {
                logger.info("cache exception ",e);
                logger.error("cache exception ",e);
                e.printStackTrace();
            }
    }


}
