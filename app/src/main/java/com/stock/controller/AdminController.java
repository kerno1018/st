package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stock.bean.Keys;
import com.stock.entity.User;
import com.stock.login.AutoLogin;
import com.stock.service.UserService;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by kerno on 1/3/2016.
 */
@RestController
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService service;
    @RequestMapping("/admin/updatePremium")
    @ResponseBody
    public String updatePreminumCondition(@RequestParam("premium") String preminum){
        try{
            if(!StringUtils.isEmpty(preminum) && Double.valueOf(preminum) > 0.1){
                logger.info("already change premium condition to " + preminum);
                Keys.CONDITION_PREMINUM = Double.valueOf(preminum);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Keys.SUCCESS_INFO;
    }

    @RequestMapping("/admin/showLog")
    @ResponseBody
    public void showLog(){
        Keys.SHOW_LOG = !Keys.SHOW_LOG;
        logger.info("change showLog to "+Keys.SHOW_LOG);
    }

    @RequestMapping("/admin/updateStrategy")
    public String updateStrategy(@RequestParam("strategy") Integer strategy){
        if(strategy != null){
            Keys.STRATEGY_TYPE = strategy;
            logger.warn("change strategy to " + strategy);
        }
        return Keys.SUCCESS_INFO;
    }

    @RequestMapping("/admin/getPrepareData")
    public String getOperateMoney(){

        StringBuilder str = new StringBuilder();
        ObjectMapper obj = new ObjectMapper();
        ObjectNode node  =  obj.createObjectNode();
        node.put("premium",Keys.CONDITION_PREMINUM);
        node.put("strategy",Keys.STRATEGY_TYPE);
        node.put("operateMoney",Keys.SELL_THRESHOLD*2);
        try {
            return obj.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }
    @RequestMapping("/admin/updateOperateMoney")
    public String setOperateMoney(@RequestParam("operateMoney") Double operateMoney){
        if(operateMoney != null){
            Keys.SELL_THRESHOLD = operateMoney/2;
        }
        return Keys.SUCCESS_INFO;
    }

    @RequestMapping("/admin/login")
    public void autoLogin(@RequestParam("id") Integer id){
        User user = service.getUserInfo(id);
        if(user != null && !StringUtil.isBlank(user.getLoginAccount()) && !StringUtil.isBlank(user.getLoginPassword())){
            new Thread(new AutoLogin(service,user)).start();
        }
    }

}
