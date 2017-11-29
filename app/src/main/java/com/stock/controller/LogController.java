package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stock.entity.LogInfo;

import com.stock.entity.LogRoll;
import com.stock.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kerno on 1/3/2016.
 */
@RestController
public class LogController {
    @Autowired
    private LogService service;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/log/getAllLog")
    @ResponseBody
    public String allRollLog() {
        Map<String,String[]> params=request.getParameterMap();
        String searchValue = "";
        String searchDate="";
        String own="";
        String advise="";
        Integer start=0;
        Integer length=0;
        String orderType = "desc";
        String orderColumn = " id ";
        for(String attr:params.keySet()){
            String[] val=params.get(attr);
            if(attr.equals("start"))
                start=Integer.parseInt(val[0]);
            if(attr.equals("length"))
                length=Integer.parseInt(val[0]);
            if(attr.equals("search[value]"))
                searchValue=val[0];
            if(attr.equals("columns[12][search][value]"))
                searchDate=val[0];
            if(attr.equals("columns[0][search][value]"))
                own=val[0];
            if(attr.equals("columns[1][search][value]"))
                advise=val[0];
            if(attr.equals("order[0][column]")){
                if(2 == Integer.parseInt(val[0])){
                    orderColumn = " (ownOnePremium - adviseSellOnePremium) ";
                }
            }
            if(attr.equals("order[0][dir]")){
                orderType = val[0];
            }

        }

        List<LogRoll> result = service.getAllLogInfo(start,length,searchValue,searchDate,own,advise,orderColumn,orderType);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "{\"data\":";
        try {
           jsonResult += mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jsonResult+="}";

        return jsonResult;
    }
    @RequestMapping("/log/getDealLog")
    @ResponseBody
    public List<LogInfo> getLogByUser(HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return new ArrayList<>();
        }
        return service.getDealLogByUser(Integer.valueOf(userId.toString()));
    }

    @RequestMapping("/log/getSquareLog")
    @ResponseBody
    public List<LogInfo> getSquareLog(HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return new ArrayList<>();
        }
        return service.getDealLogByUser(Integer.valueOf(userId.toString()));
    }

}
