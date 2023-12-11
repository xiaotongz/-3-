package com.zjweu;

import com.zjweu.filter.LoginCheckFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<LoginCheckFilter> loggingFilter(){
        FilterRegistrationBean<LoginCheckFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoginCheckFilter());
        registrationBean.addUrlPatterns("/*"); // adjust this to your needs

        return registrationBean;
    }

}
