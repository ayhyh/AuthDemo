package com.example.demo1.controller;

import com.example.demo1.consts.ConstCode;
import com.example.demo1.service.UserService;
import com.example.demo1.service.RedisServce;
import com.example.demo1.util.base.Msg;
import com.example.demo1.util.tokenMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public Map getAllUser(){

        return userService.getAllUserFromDB();

    }
    @RequestMapping(value = "/user/{username}",method = RequestMethod.GET)
    public String getUserByUsername(@PathVariable String username){
        for(Map.Entry<String, String> entry : userService.getAllUserFromDB().entrySet()){
            if(entry.getKey().trim().equals(username.trim())){
                return  entry.getValue();
            }
        }
        return "no this user";
    }

}
