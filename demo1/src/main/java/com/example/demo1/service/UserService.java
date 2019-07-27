package com.example.demo1.service;

import com.example.demo1.entity.UriRule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    Map<String,String> userMap = new HashMap<String,String>();
    Map<String,List<UriRule>> userUriRuleMap = new HashMap<>();
    public Map<String,String> getAllUserFromDB(){

        initUserFromDB();

        return userMap;

    }

    private void initUserFromDB() {
        userMap.put("test1","test1");
        userMap.put("test2","test2");
    }





    public List<UriRule> getALLAuthFromDB() {
        UriRule uriRule6=new UriRule("/users","GET");
        UriRule uriRule7=new UriRule("/user","POST");
        UriRule uriRule8=new UriRule("/user","DELETE");
        UriRule uriRule9=new UriRule("/user/{*}","GET");
        List<UriRule> uriRuleList1=new ArrayList<>();
        uriRuleList1.add(uriRule6);
        uriRuleList1.add(uriRule7);
        uriRuleList1.add(uriRule8);
        uriRuleList1.add(uriRule9);
        return uriRuleList1;

    }

    private void initAuthFromDB() {

        UriRule uriRule1=new UriRule("/users","GET");
        UriRule uriRule2=new UriRule("/user","POST");
        UriRule uriRule3=new UriRule("/user","DELETE");
        UriRule uriRule4=new UriRule("/user/{*}","GET");
        List<UriRule> uriRuleList=new ArrayList<>();
        uriRuleList.add(uriRule1);
        uriRuleList.add(uriRule2);
        uriRuleList.add(uriRule3);
        uriRuleList.add(uriRule4);
        userUriRuleMap.put("test1",uriRuleList);


        UriRule uriRule5=new UriRule("/user/{*}","GET");
        List<UriRule> uriRuleList1=new ArrayList<>();
        uriRuleList1.add(uriRule5);
        userUriRuleMap.put("test2",uriRuleList1);

    }

    public List<UriRule> getUserAuthFromDB(String username) {
        initAuthFromDB();
        for(Map.Entry<String, List<UriRule>> entry : userUriRuleMap.entrySet()){

            if(entry.getKey().equals(username)){
                return entry.getValue();
            }

        }
        return  null;

    }
}
