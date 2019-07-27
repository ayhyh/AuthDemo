package com.example.demo1.entity;

import java.util.Objects;

public class UriRule{

    String url;
    String method;

    public UriRule(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UriRule uriRule = (UriRule) o;
        return Objects.equals(url, uriRule.url) &&
                Objects.equals(method, uriRule.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

    @Override
    public String toString() {
        return "UriRule{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
