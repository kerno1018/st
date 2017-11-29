package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.entity.Fluctuate;
import com.stock.entity.Grid;
import com.stock.entity.MonthToMonth;
import com.stock.service.FluctuateService;
import com.stock.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by kerno on 1/10/2016.
 */
@RestController
public class FluctuateController {

    @Autowired
    private FluctuateService service;
    @Autowired
    private HttpServletRequest request;



    @RequestMapping(value="/fluctuate/getAll" , method = RequestMethod.GET)
    @ResponseBody
    public String getFluctuateInfo(HttpServletResponse response, HttpSession session) throws Exception {
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return "{\"data\":[]}";
        }
        Map<String,String[]> params=request.getParameterMap();
        Integer start=0;
        Integer length=0;
        String orderColumn="date";
        String orderType = "desc";
        String searchDate="";
        String code = "";
        for(String attr:params.keySet()){
            String[] val=params.get(attr);
            if(attr.equals("start"))
                start=Integer.parseInt(val[0]);
            if(attr.equals("length"))
                length=Integer.parseInt(val[0]);

            if(attr.equals("columns[5][search][value]"))
                searchDate=val[0];
            if(attr.equals("columns[0][search][value]"))
                code=val[0];
            if(attr.equals("order[0][dir]")){
                orderType = val[0];
            }
            if(attr.equals("order[0][column]")){
                if(1 == Integer.parseInt(val[0])){
                    orderColumn = "increase";
                }
            }
        }
        List<Fluctuate> result = service.getAllFluctuates(start,length,code,searchDate,orderColumn,orderType);
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

    @RequestMapping(value="/month/getAll" , method = RequestMethod.GET)
    @ResponseBody
    public String getMonthInfo(HttpServletResponse response, HttpSession session) throws Exception {
        Map<String,String[]> params=request.getParameterMap();
        Integer start=0;
        Integer length=0;
        String searchValue="";
        String orderType = "asc";
        for(String attr:params.keySet()){
            String[] val=params.get(attr);
            if(attr.equals("start"))
                start=Integer.parseInt(val[0]);
            if(attr.equals("length"))
                length=Integer.parseInt(val[0]);
            if(attr.equals("order[0][dir]")){
                orderType = val[0];
            }
        }
        List<MonthToMonth> result = service.getAllMonthToMonths(start,length,orderType);
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
}
