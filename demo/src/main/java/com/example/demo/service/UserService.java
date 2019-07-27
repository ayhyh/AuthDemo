package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    Map<String,String> userMap = new HashMap<String,String>();
    Map<String,List<String>> ApiKeyMap = new HashMap<>();
    public Map<String,String> getAllUserFromDB(){
        initUserFromDB();
        return userMap;

    }

    private void initUserFromDB() {
        userMap.put("test1","test1");
        userMap.put("test2","test2");
    }


    public List<String> getUserAuth(String username) {
        initAuthFromDB();
        for(Map.Entry<String, List<String>> entry : ApiKeyMap.entrySet()){

            if(entry.getKey().equals(username)){
                return entry.getValue();
            }

        }
        return  null;
    }

    private void initAuthFromDB() {

        List<String> authList1=new ArrayList<>();
        authList1.add("USER:ALLUSER");
        authList1.add("USER:ADDUSER");
        authList1.add("USER:UPDATEUSER");
        ApiKeyMap.put("test1",authList1);
        List<String> authList2=new ArrayList<>();
        authList2.add("USER:ADDUSER");
        authList2.add("USER:UPDATEUSER");
        ApiKeyMap.put("test2",authList2);
    }
}
