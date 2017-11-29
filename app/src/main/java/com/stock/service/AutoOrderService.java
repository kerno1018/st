package com.stock.service;

import com.stock.entity.AutoOrder;
import com.stock.util.DB;
import com.stock.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.AutoOrderService")
public class AutoOrderService {
    private Logger logger = LoggerFactory.getLogger(AutoOrderService.class);

    @Autowired
    private Dao dao;

    public void saveOrUpdate(AutoOrder order){
        if(order.getId() == null){
            order.setExecuteStatus(false);
        }
        dao.getSession().saveOrUpdate(order);
        prepareData();
    }

    public List<AutoOrder> getAutoOrderByUser(Integer userId, Integer start, Integer length,
        String orderType, String searchValue){
        String hql = "from AutoOrder where userId=:userId";
        if(!StringUtils.isEmpty(searchValue)){
            hql += " and stock like '"+searchValue+"%'";
        }
        hql +=" order by operateTime " + orderType;
        return dao.getSession().createQuery(hql).setParameter("userId",userId).setFirstResult(start).setMaxResults(length).list();
    }

    public List<AutoOrder> getAutoOrderByUser(){
        String hql = "from AutoOrder where executeStatus = false";
        return dao.getSession().createQuery(hql).list();
    }

    public void deleteRecordByIds(Integer userId,String rmIds) {
        if(StringUtils.isEmpty(rmIds)){
           return;
        }
        for(String key:rmIds.split(",")){
            DB.autoOrders.remove(Integer.valueOf(key));
        }
        String hql = "delete from AutoOrder where userId=:userId and id in ("+rmIds+") ";
        dao.getSession().createQuery(hql).setParameter("userId",userId).executeUpdate();
        prepareData();
    }

    public void updateRecordToDone(String orderIds){
        String hql = "update AutoOrder set executeStatus=true where id in ("+orderIds+")";
        dao.getSession().createQuery(hql).executeUpdate();
    }

    public void prepareData() {
        DB.autoOrders.clear();
        List<AutoOrder> result = getAutoOrderByUser();
        if(result != null && result.size()>0){
            for(AutoOrder order : result){
                DB.autoOrders.put(order.getId(),order);
            }
        }
        logger.info(" - prepareData - had "+result.size()+" orders already add to process and waiting to execute");
    }
}
