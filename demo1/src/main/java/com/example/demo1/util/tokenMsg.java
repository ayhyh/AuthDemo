package com.example.demo1.util;

import com.example.demo1.util.base.Msg;

public class tokenMsg extends Msg {



    String token;

    public tokenMsg(String msg, int code, Object info, String token) {

        super(msg, code, info);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public static tokenMsg getMsgObj(String msg, int code, Object info, String token){
        return new tokenMsg(msg,code,info,token);
    }
}
