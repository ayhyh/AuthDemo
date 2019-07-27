package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisServce {


    Map<String,String> onlieMap = new HashMap<String,String>();
    public void putRedis(String username, String token) {

        onlieMap.put(username,token);
    }

    public String getUsername(String token) {
        for(Map.Entry<String, String> entry : onlieMap.entrySet()){

             if(token.equals(entry.getValue())){
                 return entry.getKey();
             }

        }
        return null;
    }
}
