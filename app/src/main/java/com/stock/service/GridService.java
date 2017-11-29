package com.stock.service;

import com.stock.entity.Grid;
import com.stock.util.DB;
import com.stock.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kerno on 2016/2/5.
 */
@Service("service.GridService")
public class GridService {
    private Logger logger = LoggerFactory.getLogger(GridService.class);
    @Autowired
    private Dao dao;

    public void saveOrUpdate(Grid grid){
        if(grid.getId() == null){
            grid.setEnable(false);
        }
        dao.getSession().saveOrUpdate(grid);
        prepareDate();
    }
    public List<Grid> getGridByUser(Integer userId, Integer start, Integer length, String orderType,
        String searchValue){
        String hql = "from Grid where userId=:userId" ;

        if(!org.springframework.util.StringUtils.isEmpty(searchValue)){
            hql += " and stock like '"+searchValue+"%'";
        }
        hql += " order by stock " + orderType;
        return dao.getSession().createQuery(hql).setParameter("userId",userId).setFirstResult(start).setMaxResults(length).list();
    }

    public List<Grid> getAllGridInfo(){
        String hql = "from Grid ";
        return dao.getSession().createQuery(hql).list();
    }

    public void deleteRecordByIds(Integer userId,String rmIds) {
        String hql = "delete from Grid where userId=:userId and id in ("+rmIds+") ";
        dao.getSession().createQuery(hql).setParameter("userId",userId).executeUpdate();
        prepareDate();
    }

    public void prepareDate() {
        DB.grids.clear();
        List<Grid> result = getAllGridInfo();
        if(result != null && result.size()>0){
            for(Grid grid : result){
                DB.grids.put(grid.getId(),grid);
            }
            logger.info(" - prepareDate - had "+result.size()+" orders already add to process and waiting to execute");
        }
        List<Grid> list = getAllGridInfo();
        if(list != null){
            for(Grid grid : list){
                DB.updateStock.add(grid.getStock());
            }
        }
    }

    public void updateGridStatusById(Integer id,Boolean value) {
        String hql = "update Grid set enable=:enable where id=:id";
        dao.getSession().createQuery(hql).setParameter("enable",value).setParameter("id",id).executeUpdate();
    }

    public void reverseGridStatusById(Integer id){
        Grid grid = (Grid) dao.getSession().get(Grid.class,id);
        if(grid != null){
            grid.setEnable(!grid.getEnable());
            dao.getSession().update(grid);
        }

    }
}
