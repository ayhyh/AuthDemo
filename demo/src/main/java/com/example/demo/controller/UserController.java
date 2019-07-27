package com.example.demo.controller;

import com.example.demo.annotation.Api;
import com.example.demo.consts.ConstCode;
import com.example.demo.service.UserService;
import com.example.demo.service.RedisServce;
import com.example.demo.util.base.Msg;
import com.example.demo.util.tokenMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@ResponseBody
@RequestMapping("")
public class UserController {



    @Autowired UserService userService;
    @Autowired RedisServce redisServce;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object Userlogin(String username, String password){

        for(Map.Entry<String, String> entry : userService.getAllUserFromDB().entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            if(username.trim().equals(mapKey) && password.trim().equals(mapValue)){

                String token = UUID.randomUUID().toString().replaceAll("-", "");
                redisServce.putRedis(username.trim(),token);
                return tokenMsg.getMsgObj("success!", ConstCode.SUCCESS,"",token);
            }
        }

        return Msg.getMsgObj("username or password error!",200,"");
    }

    @Api(desc = "get all user",apiKey = "USER:ALLUSER",loginVerfify = true)
    @RequestMapping(value = "users",method = RequestMethod.GET)
    public Map getAllUser(){

        return userService.getAllUserFromDB();

    }

}
