package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.entity.PickMoney;
import com.stock.service.PickMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Created by kerno on 1/3/2016.
 */
@RestController
public class PickMoneyController {
    private static Logger logger = LoggerFactory.getLogger(PickMoneyController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PickMoneyService service;

    @RequestMapping("/pick/allPickLogs")
    @ResponseBody
    public String updatePreminumCondition(){
        Map<String,String[]> params=request.getParameterMap();
        String searchValue = "";
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
        }

        List<PickMoney> result = service.getAllPickMoneyList(start,length);

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
