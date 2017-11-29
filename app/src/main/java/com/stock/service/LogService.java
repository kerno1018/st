package com.stock.service;

import com.mysql.jdbc.StringUtils;
import com.stock.entity.EagerLogRoll;
import com.stock.entity.LogInfo;
import com.stock.entity.LogRoll;
import com.stock.dao.Dao;
import com.stock.job.util.DateUtil;
import com.stock.util.CacluateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.LogService")
public class LogService {
    @Autowired
    private Dao dao;

    public void saveLog(LogRoll log){
        dao.getSession().save(log);
    }

    public List<LogRoll> getAllLogInfo(Integer start, Integer length, String searchValue,String searchDate,String own,String advise, String orderColumn, String orderType){
        String hql = "from LogRoll where 1=1 ";
        if(!StringUtils.isNullOrEmpty(searchValue)){
            hql += " and stockA like '"+searchValue+"%'";
        }
        if(!StringUtils.isNullOrEmpty(searchDate)){
            Date date = CacluateUtil.format(searchDate);
            if(date == null){
                searchDate = "";
            }
        }
        if(!StringUtils.isNullOrEmpty(searchDate)){
            hql += " and createTime >= date_format('"+searchDate +"','%Y-%m-%d')";
        }
        if(!StringUtils.isNullOrEmpty(own)){
            hql += " and stockA like '"+own+"%'";
        }
        if(!StringUtils.isNullOrEmpty(advise)){
            hql += " and changeToA like '"+advise+"%'";
        }

        hql += " order by " + orderColumn + orderType;

        return dao.getSession().createQuery(hql).setFirstResult(start).setMaxResults(length).list();
    }


    public List<LogInfo> getDealLogByUser(Integer userId) {
        String hql = "from LogInfo where userId = :userId";

        return dao.getSession().createQuery(hql).setParameter("userId",Integer.valueOf(userId)).list();



    }

    public void saveEagerLog(EagerLogRoll log) {
        dao.getSession().save(log);
    }
}
