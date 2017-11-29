package com.stock.service;

import com.stock.bean.Keys;
import com.stock.bean.Stock;
import com.stock.entity.*;
import com.stock.util.DB;
import com.stock.dao.Dao;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stock.util.CacluateUtil;
import com.stock.util.Util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by kerno on 1/14/2016.
 */
@Service("service.StockService")
public class StockService  {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    private Dao dao;

    public void syncMapping(List<StockMap> mapping){
        if(mapping == null || mapping.size() == 0){
            return;
        }
        String hql = "select t.code from StockMap t ";
        List<String> list = dao.getSession().createQuery(hql).list();
        List<StockMap> needAdd = new LinkedList<>();
        if(mapping != null){
            for(StockMap map : mapping){
                if(!list.contains(map.getCode())){
                    needAdd.add(map);
                }
            }
        }
        if(needAdd.size() > 0){
            for(StockMap bean : needAdd){
                dao.getSession().save(bean);
            }
        }
        buildStockMapping(needAdd.size()>0);
    }


    public void buildStockMapping(Boolean force) {
        if(DB.stockMapping.size() == 0 || force){
            String hql = "from StockMap";
            List<StockMap> list = dao.getSession().createQuery(hql).list();
            for(StockMap map : list){
                DB.stockMapping.put(map.getCode(),map.getMapping());
            }
        }
    }

    public void updateStockMapping(String value) {
        String key = value.substring(0,2);
        String code = value.substring(2);
        String hql = "from StockMap t where t.code = :code";
        StockMap bean = (StockMap) dao.getSession().createQuery(hql).setParameter("code",code).uniqueResult();
        if(bean != null){
            String mapping = "sz".equals(key)?"sh":"sz";
            bean.setMapping(mapping);
            dao.getSession().saveOrUpdate(bean);
            DB.stockMapping.put(bean.getCode(),bean.getMapping());
        }
    }

    public void syncPosition(String fundIds) throws IOException {
        String url="http://www.jisilu.cn/data/sfnew/detail/";
        for(String id : fundIds.split(",")){
            if(DB.getMj(id) == null){
                continue;
            }
            if(DB.getMj(id).getPosition() != null){
                continue;
            }
            String response = Util.getResponseByUrl(url+id);
            org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
            Elements elements = doc.getElementsByTag("table").get(1).children().get(0).children().get(0).children().get(2).getElementsByTag("tbody").get(0).getElementsByTag("tr");
            Elements td = elements.get(0).getElementsByTag("td");
            if(DB.getMj(id).getPosition() == null){
                DB.getMj(id).setPosition(Double.valueOf(td.get(5).text().replaceAll("%",""))/100);
                logger.info("position : " + id + " had " + DB.getMj(id).getPosition());
            }
        }
    }

    public void syncYesterdayFundNetValue(String fundIds) throws IOException {

        String url="http://www.jisilu.cn/data/sfnew/detail/";

        String hql = "from LogInfo where sellTime <:today and (realBuyFNV=null or realSellFNV=null)";
        List<LogInfo> result = dao.getSession().createQuery(hql).setParameter("today",CacluateUtil.getToday()).list();
        for(String id : fundIds.split(",")){
            if(DB.getMj(id) == null){
                continue;
            }
            String response = Util.getResponseByUrl(url+id);
            org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
            Elements elements = doc.getElementsByTag("table").get(1).children().get(0).children().get(0).children().get(2).getElementsByTag("tbody").get(0).getElementsByTag("tr");
            Elements td = elements.get(0).getElementsByTag("td");
            if(td != null){
                Double fundNetValue = Double.valueOf(td.get(2).text().trim());
//                String date = CacluateUtil.getDateByMD(td.get(0).text().substring(0,5));
                for(LogInfo log : result){
                    if(DB.getAllMJ().get(id).getFundA_id().equals(log.getSellStockA())){
                        log.setRealSellFNV(fundNetValue);
                    }
                    if(DB.getAllMJ().get(id).getFundA_id().equals(log.getBuyStockA())){
                        log.setRealBuyFNV(fundNetValue);
                    }
                }
            }
        }
        for(LogInfo log : result){
            dao.getSession().merge(log);
        }
    }

    public void deleteDuplicateRecord(){
        String hql = "from StockHistory group by stockId,tradeDate having count(*)>1";
        List<StockHistory> list = dao.getSession().createQuery(hql).list();
        for(StockHistory history : list){
            dao.getSession().delete(history);
        }
        hql = "from StockValue group by code,createDate having count(*)>1";
        List<StockValue> values = dao.getSession().createQuery(hql).list();
        for(StockValue value : values){
            dao.getSession().delete(value);
        }


    }

    public void saveHistory(StockHistory history) {
        dao.getSession().save(history);
    }

    public void flushSession(){
        try{
            dao.getSession().flush();
            dao.getSession().clear();

        }catch (Exception ex){
            System.out.println("flash error");
        }
    }

    public List<AllStockCodeMapping> getAllStockMap(){
        String hql = "from AllStockCodeMapping t where t.mapping != '' ";
        return dao.getSession().createQuery(hql).list();
    }

    public List<AllStockCodeMapping> getStockMappByIds(String ids){
        String hql = "from AllStockCodeMapping t where t.id in("+ids+")";
        return dao.getSession().createQuery(hql).list();
    }

    public List<AllStockCodeMapping> getFailedSyncRecord(){

        String sql = "select * from ALL_STOCK_CODE_MAPPING where id not in (select stock_id from STOCK_HISTORY group by stock_id) ";

        return dao.getSession().createSQLQuery(sql).addEntity(AllStockCodeMapping.class).list();

    }
    public List<AllStockCodeMapping> getHasValueStockMap(){
        String hql = "from AllStockCodeMapping t where t.hasValue = true or t.hasValue is null";
        return dao.getSession().createQuery(hql).list();
    }

    public void saveStockValue(StockValue stockValue){
        String hql = "from StockValue where code=:code and createDate >=:createDate";
        List<StockValue> stockValues = dao.getSession().createQuery(hql).setParameter("code",stockValue.getCode()).setParameter("createDate",stockValue.getCreateDate()).list();
        if(stockValues.size() == 0 ){
            dao.getSession().saveOrUpdate(stockValue);
        }
    }

    public void updateStockValue(){
        List<AllStockCodeMapping> list = getHasValueStockMap();
        System.out.println("update stock value "+list.size()+" processing start");
        for(int i=0;i<list.size();i++){
            System.out.println("update stock value :" + (i +1));
            try {
                list.get(i).setHasValue(buildStockValue(list.get(i).getCode(),list.get(i).getMapping(),list.get(i).getId()));
            } catch (IOException e) {
                list.get(i).setHasValue(false);
            }
            dao.getSession().update(list.get(i));
            if(i % 500 == 0){
                flushSession();
            }
        }
        System.out.println("update stock value "+list.size()+" processing done");
    }

    public void deleteTodayStockValue(){
        String hql = "delete from StockValue where createDate>=:createDate ";
        dao.getSession().createQuery(hql).setParameter("createDate",CacluateUtil.getToday()).executeUpdate();
    }

    public void autoMappingStock(String code) {

        String szResponse = Util.getResponseByUrl(Keys.URL_STOCKK+"sz"+code);
        String shResponse = Util.getResponseByUrl(Keys.URL_STOCKK+"sh"+code);
        String mapping="";
        if(szResponse.length() > 50 && shResponse.length()>50){
            saveMapping(code,"sh");
            saveMapping(code,"sz");
        }else if(szResponse.length()>50){
            saveMapping(code,"sz");
        }else if(shResponse.length()>50){
            saveMapping(code,"sh");
        }else{
            String hkResponse = Util.getResponseByUrl(Keys.URL_STOCKK+"hk"+code);
            if(hkResponse.length() > 50){
                saveMapping(code,"hk");
            }
        }
    }

    private void saveMapping(String code,String mapping){
        String hql = "from AllStockCodeMapping t where t.code = :code and mapping=:mapping";
        AllStockCodeMapping bean = (AllStockCodeMapping) dao.getSession().createQuery(hql).setParameter("code",code).setParameter("mapping",mapping).uniqueResult();
        if(bean == null){
            bean = new AllStockCodeMapping();
            bean.setCode(code);
            bean.setMapping(mapping);
            dao.getSession().save(bean);
            System.out.println(bean.getCode());
        }
    }

    // update stock value every day
    public boolean buildStockValue(String code, String mapping,Integer stockId) throws IOException {
        String url="http://gupiao.baidu.com/stock/"+mapping+code+".html?from=aladingpc";
        String response = Util.getResponseByUrl(url);
        org.jsoup.nodes.Document doc = Jsoup.parse(response, "UTF-8");
        Elements bean = doc.getElementsByTag("dd");
        if(bean.size()>=22){
            Pattern p = Pattern.compile("[^0-9|^.]");
            StockValue worth = new StockValue();
            worth.setStockId(stockId);
            if(!bean.get(1).text().equals("--"))
                worth.setDealVolume(Double.valueOf(p.matcher(bean.get(1).text()).replaceAll("")));// volume

            if(!bean.get(7).text().equals("--"))
                worth.setFollowMarketValue( Double.valueOf(p.matcher(bean.get(7).text()).replaceAll("")));// follow market value

            if(!bean.get(8).text().equals("--"))
                worth.setPe(Double.valueOf( p.matcher(bean.get(8).text()).replaceAll("")));// pe

            if(!bean.get(9).text().equals("--"))
                worth.setEachWorth(Double.valueOf(p.matcher(bean.get(9).text()).replaceAll("")));// each stock worthh

            if(!bean.get(10).text().equals("--"))
                worth.setTotalEquip( Double.valueOf(p.matcher(bean.get(10).text()).replaceAll("")));/// total equity

            if(!bean.get(12).text().equals("--"))
                worth.setTurnOver( Double.valueOf(p.matcher(bean.get(12).text()).replaceAll("")));/// change chance

            if(!bean.get(18).text().equals("--"))
                worth.setTotalMarketValue( Double.valueOf(p.matcher(bean.get(18).text()).replaceAll("")));/// all market value

            if(!bean.get(19).text().equals("--"))
                worth.setPb( Double.valueOf(p.matcher(bean.get(19).text()).replaceAll("")));/// pb

            if(!bean.get(20).text().equals("--"))
                worth.setEachNetAsset( Double.valueOf(p.matcher(bean.get(20).text()).replaceAll("")));/// pb

            if(!bean.get(21).text().equals("--"))
                worth.setFollowEquip(Double.valueOf( p.matcher(bean.get(21).text()).replaceAll("")));/// follow equity

            String date = doc.getElementsByAttributeValue("class","stock-bets").get(0).child(0).child(1).text();
            worth.setCreateDate(CacluateUtil.format(date.split(" ")[1]));
            worth.setCode(code);
            dao.getSession().save(worth);
            return true;
        }
        return false;
    }

    public void deleteTodayStockHistory(){
        String hql ="delete from StockHistory where tradeDate>=:today";
        dao.getSession().createQuery(hql).setParameter("today",CacluateUtil.getToday()).executeUpdate();
    }
    public void addTodayStockHistory(List<AllStockCodeMapping> allStockMap) {
        int i = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("history added start");
        for(AllStockCodeMapping map : allStockMap){
            System.out.println("history added : " + ++i);
            String stock = Util.getResponseByUrl(Keys.URL_STOCKK+map.getMapping()+map.getCode());
            if(stock.length() < 30){
                // TODO fix error code
                continue;
            }
            StockHistory history = new StockHistory();
            history.setStockId(map.getId());
            String id = stock.substring(13,stock.indexOf("="));
            if(stock.startsWith("var hq_str_hkHSI=")){
                stock = stock.replaceAll("Hang Seng Main Index,","");
            }
            if(stock.startsWith("var hq_str_hkHSCEI=")){
                stock = stock.replaceAll("Hang Seng China Enterprises Index,","");
            }
            int prex = 21;
            if("HSI".equals(id)){
                System.out.println("HSI?");
                prex=18;
            }
            stock = stock.substring(prex,stock.lastIndexOf("\""));
            String[] fields = stock.split(",");
//            if(StringUtils.isEmpty(map.getName())){
//                map.setName(fields[0]);
////                map.setName("中国");
////                System.out.println(fields[0]);
////                dao.getSession().update(map);
//            }
            try{
                history.setOpenPrice(Double.valueOf(fields[1]));
                history.setYesterdayClosePrice(Double.valueOf(fields[2]));
                history.setHighestPrice(Double.valueOf(fields[4]));
                history.setClosePrice(Double.valueOf(fields[3]));
                history.setLowestPrice(Double.valueOf(fields[5]));

                history.setTurnOverVolume(Double.valueOf(fields[8]));
                history.setTurnOverValue(Double.valueOf(fields[9]));
                if(fields.length<=20){
                    try {
                        history.setTradeDate(format.parse(fields[16]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    history.setTradeDate(CacluateUtil.format(fields[30]));
                }
                StockHistory check = (StockHistory) dao.getSession().createQuery("from StockHistory where stockId=:stockId and tradeDate=:tradeDate")
                        .setParameter("stockId",history.getStockId()).setParameter("tradeDate",history.getTradeDate()).uniqueResult();

                if(check == null ){
                    dao.getSession().save(history);
                }
            }catch (Exception ex){
                continue;
            }


            if(i%500 ==0){
                flushSession();
            }
        }
        System.out.println("history added end");
    }

    public void updateAllStockPrice() {
        String hql = "from AllStockCodeMapping";
        List<AllStockCodeMapping> list = dao.getSession().createQuery(hql).list();
        for(AllStockCodeMapping mapping : list){
            DB.allStockMapping.put(mapping.getMapping()+mapping.getCode(),mapping.getMapping());
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for(String key : DB.allStockMapping.keySet()){
            i++;
            builder.append(key).append(",");
            if(i%300 == 0){
                String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
                updateCurrentPrice(responseText);
                builder = new StringBuilder();
            }
        }
        if(builder.length() > 0){
            String responseText = Util.getResponseByUrl(Keys.URL_STOCKK+builder.toString());
            updateCurrentPrice(responseText);
        }
    }
    private void updateCurrentPrice(String source){
        String[] stocks = source.split(";");
        Stock fund = null;
        for(String stock : stocks){
            stock = stock.replaceAll("\\\\s*|\\t|\\r|\\n","");
            if(stock.length() < 30){
                // TODO fix error code
                if(!StringUtil.isBlank(stock)){
                }
                continue;
            }
            if(stock.startsWith("var hq_str_hkHSI=")){
                stock = stock.replaceAll("Hang Seng Main Index,","");
            }
            if(stock.startsWith("var hq_str_hkHSCEI=")){
                stock = stock.replaceAll("Hang Seng China Enterprises Index,","");
            }
            String mapping = stock.substring(11,stock.indexOf("="));
            String id = stock.substring(13,stock.indexOf("="));
            int prex = 21;
            if("HSI".equals(id)){
                prex=18;
            }
            try{
                stock = stock.substring(prex,stock.lastIndexOf("\""));
                String[] fields = stock.split(",");
                DB.currentStockValue.put(mapping,Double.valueOf(fields[3]));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
