package com.example.demo.config;

import com.example.demo.interceptor.Authinteptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration()
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.setAuthinteptorBean()).
                addPathPatterns("/**");// 拦截所有路径

    }
    @Bean
    public Authinteptor setAuthinteptorBean(){
        System.out.println("注入了handler");
        return new Authinteptor();
    }




}
