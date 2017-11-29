package com.stock.service;

import com.mysql.jdbc.StringUtils;
import com.stock.bean.Stock;
import com.stock.dao.Dao;
import com.stock.entity.Fluctuate;
import com.stock.entity.MonthToMonth;
import com.stock.entity.StockHistory;
import com.stock.entity.StockHistoryView;
import com.stock.util.CacluateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.FluctuateService")
public class FluctuateService {
    @Autowired
    private Dao dao;

    public List<MonthToMonth> getAllMonthToMonths(Integer start, Integer length, String orderType){
        return dao.getSession().createQuery("from MonthToMonth order by dealDate "+orderType).setFirstResult(start).setMaxResults(length).list();
    }
    public List<Fluctuate> getAllFluctuates(Integer start, Integer length, String code, String searchDate, String orderColumn, String orderType){
        String hql = "from Fluctuate where 1=1 ";
        if(!StringUtils.isNullOrEmpty(searchDate)){
            Date date = CacluateUtil.format(searchDate);
            if(date == null){
                searchDate = "";
            }
        }
        if(!StringUtils.isNullOrEmpty(code)){
            hql += " and code like '" +code+"%' ";
        }
        if(!StringUtils.isNullOrEmpty(searchDate)){
            hql += " and date >= date_format('"+searchDate +"','%Y-%m-%d')";
        }
        hql += " order by "+orderColumn+ " "+orderType;
        return dao.getSession().createQuery(hql).setFirstResult(start).setMaxResults(length).list();
    }


    public void getFluctuateData(){

        String sql = "from StockHistory where stockId=1308 order by tradeDate desc";

        List<StockHistory> list = dao.getSession().createQuery(sql).setMaxResults(2).list();
        List<Fluctuate> check = dao.getSession().createQuery("from Fluctuate where date=:tradeDate").setParameter("tradeDate",list.get(0).getTradeDate()).setMaxResults(1).list();
        if(check.size()>0){
            return;
        }

        sql = "select * from STOCK_HISTORY history left join ALL_STOCK_CODE_MAPPING mapp on history.stock_id=mapp.id where history.trade_date = :tradeDate and mapp.id in(" +
                "3837,3838,3840,3841,3842,3843,3845,3846,3852,3853,3854,3855,3856,3857,3864,3867,3871,3883,3886,3887,3891,3893,3897,3901,3902,3903,3956,3957,1803,1738,1331,1305) order by history.trade_date desc";

        List<StockHistoryView> historyResult = dao.getSession().createSQLQuery(sql).addEntity(StockHistoryView.class).setParameter("tradeDate",list.get(0).getTradeDate()).list();
        List<Fluctuate> result = new LinkedList<>();
        Fluctuate bean = null;
        for(int i=0;i<historyResult.size();i++){
            List<StockHistory> tempHistory = dao.getSession().createQuery("from StockHistory where stockId=:id and tradeDate=:date ").setParameter("id",historyResult.get(i).getStock().getId()).setParameter("date",list.get(1).getTradeDate()).list();
            StockHistoryView history = historyResult.get(i);
            bean = new Fluctuate();
            bean.setCode(history.getStock().getCode()+"");
            bean.setDate(history.getTradeDate());
            bean.setIncrease(CacluateUtil.format((history.getClosePrice() - history.getYesterdayClosePrice())/history.getYesterdayClosePrice(),4)*100+"");
            bean.setPrice(history.getClosePrice());
            if(tempHistory != null && tempHistory.size() == 1){
                bean.setYesterdayVolume(tempHistory.get(0).getTurnOverVolume());
            }
            bean.setVolume(history.getTurnOverVolume());

            dao.getSession().save(bean);
        }


    }
    public void getMonthData(){

        String sql = "from StockHistory where stockId=1308 order by tradeDate desc";

        List<StockHistory> list = dao.getSession().createQuery(sql).setMaxResults(2).list();
        List<MonthToMonth> check  = dao.getSession().createQuery("from MonthToMonth where dealDate=:date").setParameter("date",list.get(0).getTradeDate()).list();
        if(check.size()>0){
            return;
        }
        List<MonthToMonth> monthList  = dao.getSession().createQuery("from MonthToMonth where dealDate=:date").setParameter("date",list.get(1).getTradeDate()).list();


//        sql = "select mapp.code,history.close_price,history.open_price,(history.close_price-history.open_price)/history.open_price from STOCK_HISTORY history left join ALL_STOCK_CODE_MAPPING mapp on history.stock_id=mapp.id where history.trade_date = :tradeDate and history.open_price > 0 order by history.trade_date  ";

//            List<Object[]> historyResult = dao.getSession().createSQLQuery(sql).setParameter("tradeDate",list.get(1).getTradeDate()).list();
        List<StockHistory> result = dao.getSession().createQuery("from StockHistory where tradeDate=:tradeDate and openPrice>0 and closePrice>0 ").setParameter("tradeDate",list.get(0).getTradeDate()).list();
//        600 601 603 000 002 300
        List<Integer> stockCodes = dao.getSession().createQuery("select id from AllStockCodeMapping where code like '600%' or code like '601%' or code like '603%' or code like '000%' or code like '002%' or code like '300%'").list();
        int limitUpCount =0;
        int limitUp5Count =0;
        int limitDownCount=0;
        int limitDown5Count=0;
        for (StockHistory history: result){
            if(stockCodes.contains(history.getStockId())){
                    Double limitUp = CacluateUtil.format(history.getYesterdayClosePrice()*1.1,2);
                    Double limitDown =CacluateUtil.format(history.getYesterdayClosePrice()*0.9,2);
                    Double fluctuate = CacluateUtil.format((history.getClosePrice() - history.getYesterdayClosePrice() )/history.getClosePrice(),2);

                    if(history.getClosePrice()>=limitUp){
                        limitUpCount +=1;
                    }
                    if(history.getClosePrice()<=limitDown){
                        limitDownCount +=1;
                    }
                    if(fluctuate >=0.005){
                        limitUp5Count +=1;
                    }
                    if(fluctuate <=-0.05){
                        limitDown5Count += 1;
                    }
            }
        }
        MonthToMonth value = new MonthToMonth();
        if(monthList.size()>0){
            value.setYlimitDown(monthList.get(0).getLimitDown());
            value.setYlimitUp(monthList.get(0).getLimitUp());
            value.setYlimitDown5(monthList.get(0).getLimitDown5());
            value.setYlimitUp5(monthList.get(0).getLimitUp5());
        }

        value.setDealDate(list.get(0).getTradeDate());
        value.setLimitDown(limitDownCount);
        value.setLimitUp(limitUpCount);
        value.setLimitDown5(limitDown5Count);
        value.setLimitUp5(limitUp5Count);


        dao.getSession().save(value);
//        System.out.println(limit5);


    }


}
