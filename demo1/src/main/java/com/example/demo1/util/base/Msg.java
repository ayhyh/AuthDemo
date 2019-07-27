package com.example.demo1.util.base;

public class Msg {
    public Msg() {
    }

    String msg;

    public Object getInfo() {
        return tokon;
    }

    public void setInfo(Object info) {
        this.tokon = info;
    }

    Object tokon;
    public Msg(String msg, int code,Object info) {
        this.msg = msg;
        this.code = code;
        this.tokon =info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    int code;
    public static Msg  getMsgObj(String msg, int code,Object info){
        return new Msg(msg,code,info);
    }
}
