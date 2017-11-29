package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stock.entity.Grid;
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
public class GridController {

    @Autowired
    private GridService gridService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/grid/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public String saveOrUpdate(HttpSession session,@RequestBody Grid grid) {
        Object userId = session.getAttribute("userId");
        grid.setUserId(Integer.valueOf(userId.toString()));
        gridService.saveOrUpdate(grid);
        return "{\"success\":true}";
    }
    @RequestMapping(value = "/grid/removeByIds",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String removeByIds(HttpSession session, @RequestParam("rmIds") String rmIds) {
        Object userId = session.getAttribute("userId");
        gridService.deleteRecordByIds(Integer.valueOf(userId.toString()),rmIds);
        return "{\"success\":true}";
    }


    @RequestMapping(value="/grid/getGridInfo" , method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo(HttpServletResponse response, HttpSession session) throws Exception {
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return "{\"data\":[]}";
        }
        Map<String,String[]> params=request.getParameterMap();
        Integer start=0;
        Integer length=0;
        String orderType = "desc";
        String orderColumn = " id ";
        String searchValue="";
        for(String attr:params.keySet()){
            String[] val=params.get(attr);
            if(attr.equals("start"))
                start=Integer.parseInt(val[0]);
            if(attr.equals("length"))
                length=Integer.parseInt(val[0]);
            if(attr.equals("order[0][dir]")){
                orderType = val[0];
            }
            if(attr.equals("search[value]")){
                searchValue=val[0];
            }

        }
        List<Grid> result = gridService.getGridByUser(Integer.valueOf(userId.toString()),start,length,orderType,searchValue);
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
