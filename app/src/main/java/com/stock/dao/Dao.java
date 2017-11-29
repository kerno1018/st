package com.stock.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by kerno on 16-1-5.
 */

@Repository("Dao")
public class Dao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){

        return sessionFactory.getCurrentSession();
    }
}
