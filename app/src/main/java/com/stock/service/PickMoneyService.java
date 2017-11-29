package com.stock.service;

import com.stock.dao.Dao;
import com.stock.entity.AutoOrder;
import com.stock.entity.PickMoney;
import com.stock.util.CacluateUtil;
import com.stock.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.PickMoneyService")
public class PickMoneyService {
    private Logger logger = LoggerFactory.getLogger(PickMoneyService.class);

    @Autowired
    private Dao dao;

    public void update(PickMoney pickBean){
        dao.getSession().update(pickBean);
    }

    public void save(PickMoney bean){
        String hql = " from PickMoney where fund=:fund and createTIME>=:createTIME ";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bean.getCreateTIME());
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)-1);
        System.out.println(calendar.getTime());
        List<PickMoney> duplicateList = dao.getSession().createQuery(hql).setParameter("fund",bean.getFund()).setParameter("createTIME", calendar.getTime()).list();

        if(duplicateList == null || duplicateList.size()==0){
            dao.getSession().save(bean);
        }
    }

    public List<PickMoney> getYesterdayPickMoneyList(){

        String hql = "from PickMoney where sellSII = null and createTIME < :createTime";

        List<PickMoney> result = dao.getSession().createQuery(hql).setParameter("createTime", CacluateUtil.getToday()).list();

        return result;

    }

    public List<PickMoney> getAllPickMoneyList(Integer start, Integer length){

        String hql = "from PickMoney";

        List<PickMoney> result = dao.getSession().createQuery(hql).setFirstResult(start).setMaxResults(length).list();

        return result;

    }


}
