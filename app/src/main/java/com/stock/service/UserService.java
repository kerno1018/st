package com.stock.service;

import com.stock.entity.Account;
import com.stock.entity.LogInfo;
import com.stock.entity.User;
import com.stock.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kerno on 16-1-12.
 */
@Service("service.UserService")
public class UserService {
    @Autowired
    private Dao dao;

    public void saveOrUpdateUser(User user){
        user.setEnable(user.getId() != null);
        dao.getSession().saveOrUpdate(user);
    }

    public Boolean registUser(User user){
        String hql = "select count(*) from User t where t.username=:username";
        Object result = dao.getSession().createQuery(hql).setParameter("username",user.getUsername()).uniqueResult();
        if(Integer.valueOf(result.toString())==0){
            user.setEnable(user.getId() != null);
            dao.getSession().saveOrUpdate(user);
        }
        return Integer.valueOf(result.toString()) == 0;
    }

    public User getUserInfo(Integer userId) {
        return (User) dao.getSession().get(User.class,userId);
    }

    public void saveOrUpdateAccount(Account account) {

        dao.getSession().saveOrUpdate(account);
    }

    public User check(User user) {

        String hql = "from User t where t.username=:name and t.password=:pwd ";

       User userBean = (User) dao.getSession().createQuery(hql).setParameter("name",user.getUsername()).setParameter("pwd",user.getPassword()).uniqueResult();

        if(userBean != null){
            return userBean;
        }else{
            return null;
        }

    }

    public List<User> getActiveUsers(){
        List<User> users = dao.getSession().createQuery("from User t where t.enable = true").list();
        return users;
    }

    public List<User> getAutoUsers(){
        List<User> users = dao.getSession().createQuery("from User t where t.auto = true and t.enable=false and t.loginPassword != null and t.loginAccount!= null ").list();
        return users;
    }

    public void lockAllAccount() {
        String hql = "update User set enable=false";
        dao.getSession().createQuery(hql).executeUpdate();
    }

    public void lockAccount(User user) {
        user.setEnable(false);
        dao.getSession().saveOrUpdate(user);
    }

    public List<User> getTestUser() {
        String hql = "from User where username='test'";
        List<User> users = dao.getSession().createQuery(hql).list();
        if(users != null){
            for(User user: users){
                hql = "from LogInfo where userId=:userId order by createTime desc";
                List<LogInfo> logs = dao.getSession().createQuery(hql).setParameter("userId",user.getId()).list();
                if(logs != null && logs.size()>0){
                    user.setLog(logs.get(0));
                }
            }
        }
        return users;
    }

    public void saveLog(LogInfo log) {
        dao.getSession().save(log);
    }

//    public void updateRollingLog() {
//        LogRoll log = new LogRoll();
//        String hql = "from LogRoll order by id desc";
//        List<LogRoll> list = dao.getSession().createQuery(hql).list();
//        if(list != null && list.size()>0){
//            log = list.get(0);
//        }
//        for(String key : AccountPremiumRecord.record.keySet()){
//            log.setStock(key);
//            log.setCountTwo(AccountPremiumRecord.record.get(key).get("2"));
//            log.setCountThree(AccountPremiumRecord.record.get(key).get("3"));
//            log.setCountFour(AccountPremiumRecord.record.get(key).get("4"));
//            log.setCountFive(AccountPremiumRecord.record.get(key).get("5"));
//            log.setCountSix(AccountPremiumRecord.record.get(key).get("2"));
//            log.setCountSeven(AccountPremiumRecord.record.get(key).get("7"));
//            log.setCountEight(AccountPremiumRecord.record.get(key).get("8"));
//            log.setCountNigh(AccountPremiumRecord.record.get(key).get("9"));
//            log.setCountTen(AccountPremiumRecord.record.get(key).get("10"));
//        }
//
//        dao.getSession().merge(log);
//
//    }
}
