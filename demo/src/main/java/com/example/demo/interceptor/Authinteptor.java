package com.example.demo.interceptor;

import com.example.demo.annotation.Api;
import com.example.demo.service.RedisServce;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.alibaba.fastjson.JSONObject;

@Configuration
public class Authinteptor extends HandlerInterceptorAdapter  {

    @Autowired
    RedisServce redisServce;
    @Autowired
    UserService userService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 配置该注解，说明不进行用户拦截  handlerMethod.getBeanType() 获取对象上的注解
            Api api = handlerMethod.getMethodAnnotation(Api.class);
            // 配置该注解，说明不进行用户拦截  handlerMethod.getMethodAnnotation（）获取方法上的注解
            if (api == null) {
                return super.preHandle(request, response, handler);
            }
            if (api != null) {
                String username = this.verfifyUserToken(request);
                if(username!=null){
                        if(api.loginVerfify()){

                            List<String> userAuthList =userService.getUserAuth(username);
                            if(userAuthList!=null){

                                for(String userAuth:userAuthList){

                                      if( !api.apiKey().trim().isEmpty() && api.apiKey().equals(userAuth) ){

                                          return super.preHandle(request, response, handler);
                                      }

                                      //throw
                                }
                                this.returnErrorContent(403,response,"没有权限");
                            }
                            this.returnErrorContent(401,response,"db没有配置权限");
                            //throw
                        }
                        return super.preHandle(request, response, handler);
                    }
                this.returnErrorContent(400,response,"token异常");

            }
        }
        return super.preHandle(request, response, handler);
    }

    private void returnErrorContent(int code, HttpServletResponse response, String msg) {

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(code);
        PrintWriter out = null;
        try {
            JSONObject res = new JSONObject();
            res.put("status", code);
            res.put("msg", msg);
            out = response.getWriter();
            out.append(res.toString());


        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            }catch (Exception e){

            }
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
