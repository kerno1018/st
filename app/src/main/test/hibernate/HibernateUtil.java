package hibernate;

import com.stock.bean.BaseFund;
import com.stock.bean.Keys;
import com.stock.dao.Dao;
import com.stock.entity.AllStockCodeMapping;
import com.stock.entity.StockHistory;
import com.stock.service.FluctuateService;
import com.stock.service.HistoryService;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import com.stock.job.StockJob;
import com.stock.util.Util;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.stock.service.PickMoneyService;
import com.stock.service.StockService;
import util.compator.BaseFundCompare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kerno on 16-1-5.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
//@Transactional
public class HibernateUtil {

//    @Autowired
//    private Dao dao;
    @Test
    public void ss() throws IOException {
        ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
        new StockJob().syncJSLFund();
        HistoryService service = (HistoryService) context.getBean("service.HistoryService");
        StockService stockService = (StockService) context.getBean("service.StockService");
        double highPrice = 0.0;
        double lowPrice = 0.0;
        double openPrice = 0.0;
        double closePrice = 0.0;
        List<StockHistory> values = service.getYesterdayData(1);
        StringBuilder ids = new StringBuilder();
        for (StockHistory stock : values) {
//            if(stock.getTurnOverValue() < 100000000){
//                continue;
//            }
            openPrice = stock.getOpenPrice();
            highPrice = stock.getHighestPrice();
            closePrice = stock.getClosePrice();
            lowPrice = stock.getLowestPrice();

            if (openPrice > lowPrice && closePrice > openPrice && ((openPrice - lowPrice ) / (closePrice-openPrice) > 2)) {

                if ((closePrice - lowPrice) / lowPrice > 0.03) {

//                    if ((openPrice - lowPrice) / (closePrice - openPrice) > 3) {
                        ids.append(stock.getStockId()).append(",");
//                    }
                }
            }
        }
        if(ids.length() > 1){
            List<AllStockCodeMapping> result = stockService.getStockMappByIds(ids.deleteCharAt(ids.length()-1).toString());
            for(AllStockCodeMapping map : result){
                System.out.println(map.getCode());
            }
        }else{
            System.out.println("none");
        }
    }

    @Test
    public void dateTest(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
        FluctuateService dao = (FluctuateService) context.getBean("service.FluctuateService");
        dao.getMonthData();

    }

    @Test
    public void check() throws UnsupportedEncodingException {
        System.out.println(CacluateUtil.format(5.2634555,2));

    }


}
