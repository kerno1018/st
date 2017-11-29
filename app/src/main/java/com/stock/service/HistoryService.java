package com.stock.service;

import com.stock.dao.Dao;
import com.stock.entity.StockHistory;
import com.stock.util.CacluateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.HistoryService")
public class HistoryService {
    @Autowired
    private Dao dao;


    public Map<String, Date> getBaseDate(){
        Map<String,Date> dateMap = new HashedMap();
        String sql = "from StockHistory where stockId=1308 order by tradeDate desc";

        List<StockHistory> list = dao.getSession().createQuery(sql).setMaxResults(120).list();

        dateMap.put("5",list.get(4).getTradeDate());
        dateMap.put("10",list.get(9).getTradeDate());
        dateMap.put("15",list.get(14).getTradeDate());
        dateMap.put("20",list.get(19).getTradeDate());
        dateMap.put("30",list.get(29).getTradeDate());
        dateMap.put("60",list.get(59).getTradeDate());
        dateMap.put("120",list.get(119).getTradeDate());

        return dateMap;
    }

    public List<StockHistory> getYesterdayData(int minus){
        String sql = "from StockHistory where stockId=1308 order by tradeDate desc";

        List<StockHistory> list = dao.getSession().createQuery(sql).setMaxResults(minus).list();

        sql = "from StockHistory where tradeDate = :date";

        Query query = dao.getSession().createQuery(sql).setParameter("date", list.get(minus-1).getTradeDate());

        return query.list();


    }

    public Map<String, List<Object[]>> getAll(){

        Map<String,Date> dateMap = getBaseDate();


//        String hql5 = "select m.code,sum(s.close_price) from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("5") +"','%Y-%m-%d') group by s.stock_id ";
//        String hql5 = "select m.code,sum(s.close_price) from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("5") +"','%Y-%m-%d') group by s.stock_id ";
        String hql20 = "select m.code,m.mapping,sum(s.close_price)/20 from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("20") +"','%Y-%m-%d') group by s.stock_id,m.mapping ";
        String hql30 = "select m.code,m.mapping,sum(s.close_price)/30 from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("30") +"','%Y-%m-%d') group by s.stock_id,m.mapping ";
        String hql60 = "select m.code,m.mapping,sum(s.close_price)/60 from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("60") +"','%Y-%m-%d') group by s.stock_id,m.mapping ";
        String hql120 = "select m.code,m.mapping,sum(s.close_price)/120 from STOCK_HISTORY s,ALL_STOCK_CODE_MAPPING m where s.stock_id=m.id and s.trade_date >= date_format('"+ dateMap.get("120") +"','%Y-%m-%d') group by s.stock_id,m.mapping ";


//        List<Object[]> list5  = (List<Object[]>) dao.getSession().createSQLQuery(hql5).list();
//        List<Object[]> list10 = (List<Object[]>) dao.getSession().createSQLQuery(hql10).list();
//        List<Object[]> list15 = (List<Object[]>) dao.getSession().createSQLQuery(hql15).list();
        List<Object[]> list20 = (List<Object[]>) dao.getSession().createSQLQuery(hql20).list();
        List<Object[]> list30 = (List<Object[]>) dao.getSession().createSQLQuery(hql30).list();
        List<Object[]> list60 = (List<Object[]>) dao.getSession().createSQLQuery(hql60).list();
        List<Object[]> list120 =(List<Object[]>) dao.getSession().createSQLQuery(hql120).list();

        Map<String,List<Object[]>> result = new HashedMap();
        result.put("20",list20);
        result.put("30",list30);
        result.put("60",list60);
        result.put("120",list120);

        return result;

    }
}
