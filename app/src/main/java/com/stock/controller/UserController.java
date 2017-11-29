package com.stock.controller;

import com.stock.entity.Account;
import com.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.stock.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by kerno on 1/10/2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/user/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public User saveOrUpdate(HttpSession session, @RequestBody User user) {
        userService.saveOrUpdateUser(user);
        return user;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public User regist(HttpSession session,@RequestBody User user) {
        userService.registUser(user);
        if(user.getId() != null){
            session.setAttribute("userId",user.getId());
        }
        return user;
    }

    @RequestMapping(value="/user/getUserInfo" , method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(HttpServletResponse response, HttpSession session, Model model) throws Exception {
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return new User();
        }
        User user = userService.getUserInfo(Integer.valueOf(userId.toString()));
        return user;
    }

    @RequestMapping(value = "/account/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Account saveOrUpdate (@ModelAttribute Account account){
        userService.saveOrUpdateAccount(account);
        return account;
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public User login(HttpSession session,@RequestBody User user) {
        User bean = userService.check(user);
        if(bean != null){
            session.setAttribute("userId",bean.getId());
            return bean;
        }else{
            return user;
        }
    }


}
