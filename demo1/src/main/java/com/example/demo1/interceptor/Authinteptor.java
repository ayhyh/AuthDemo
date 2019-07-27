package com.example.demo1.interceptor;

import com.example.demo1.consts.ConstCode;
import com.example.demo1.entity.UriRule;
import com.example.demo1.service.RedisServce;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

@Configuration
public class Authinteptor extends HandlerInterceptorAdapter  {

    @Autowired
    RedisServce redisServce;
    @Autowired
    UserService userService;

    String[] withoutLoginUrl={"/login"};


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求的uri
        String requestUri = request.getRequestURI();
        //获取请求的方式
        String requestMethod = request.getMethod().toUpperCase();
        // 不进行拦截的地址
        if (this.isStartWith(requestUri)) {
            //不进行拦截的地址放行
            return super.preHandle(request, response, handler);
        }

        if (handler instanceof HandlerMethod){

            String username = this.verfifyUserToken(request);
            if(username != null){

                //query db get all api
                List<UriRule> uriRuleAllList=userService.getALLAuthFromDB();
                //query db get api by username
                List<UriRule> uriRuleUserList=userService.getUserAuthFromDB(username);

                for(UriRule uriRuledb :uriRuleAllList){
                    String dbUrl = uriRuledb.getUrl();
                    String dbMethod =uriRuledb.getMethod();
                    //this url&method is need control ？
                    Boolean ismacth = this.macth(dbUrl,dbMethod,requestUri,requestMethod);
                    if(ismacth){

                        for(UriRule userUriRule :uriRuleUserList){
                            String userDBMethod=userUriRule.getMethod();
                            String userDBUrl= userUriRule.getUrl();
                            //match user is allow this url&method ?
                            if(this.macth(userDBUrl,userDBMethod,requestUri,requestMethod)){

                                return super.preHandle(request, response, handler);
                            }
                        }
                        returnErrorContent(403,response,"无权限");
                    }

                }
                //只要登录就可以访问的接口放行
                return super.preHandle(request, response, handler);

            }
            this.returnErrorContent(ConstCode.AUTH_FAIL,response,"token异常");


        }
        return super.preHandle(request, response, handler);
    }

    private Boolean macth(String dbUrl, String dbMethod, String uri, String method) {

        String dbUri = dbUrl.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
        String regEx = "^" + dbUri + "$";
        return (Pattern.compile(regEx).matcher(uri).find() || uri.startsWith(dbUrl + "/"))  &&  dbMethod.equals(method);
    }


    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : withoutLoginUrl) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;


    }

    private void returnErrorContent(int code, HttpServletResponse response, String msg) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(code);

        ServletOutputStream out=null;
        try {
            JSONObject res = new JSONObject();
            res.put("status", code);
            res.put("msg", msg);
            out = response.getOutputStream();
            out.write(res.toString().getBytes("utf-8"));
            out.flush();
            out.close();
        } catch (IOException e) {

        }

    }


    private String verfifyUserToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        //请求参数里面找
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if(token ==null){
            return null;
        }
        return redisServce.getUsername(token);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

}
