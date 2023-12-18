package com.zjweu.filter;


import com.alibaba.fastjson.JSONObject;
import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.BaseContext;
import com.zjweu.utils.AppJwtUtil;
import com.zjweu.common.ResponseResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");


        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("拦截到请求：{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/user/logout",
                "/user/login",
                "/user/register",
                "/email/sendEmail",
                "/common/uploadAvator"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        if(check){
            log.info("{}无需处理，可直接放行",requestURI);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //3.获取请求头中的令牌（token)。
        String jwt = request.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NOT_LOGIN,"未登录");
            //手动将对象转换为json格式的数据-------》使用阿里巴巴fastJSON的工具包（需要引入依赖）
            String notLogin = JSONObject.toJSONString(result);
            response.getWriter().write(notLogin);//使用HttpServletResponse对象返回错误的JSON数据
            return;
        }
        //5.解析token(校验jwt令牌)，如果解析失败，返回错误结果（未登录)。如果错误会报错，所以使用try...catch...
        try {
            Jws<Claims> jws = AppJwtUtil.getJws(jwt);
            Claims claims = jws.getBody();
            BaseContext.setCurrentId(((Number) claims.get("id")).longValue());
        } catch (Exception e) {//程序执行到这里说明程序执行失败--jwt令牌错误
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录的错误信息");
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID,"无效token");//前端需要响应一个json格式的数据
            //手动将对象转换为json格式的数据-------》使用阿里巴巴fastJSON的工具包（需要引入依赖）
            String notLogin = JSONObject.toJSONString(result);
            response.getWriter().write(notLogin);//使用HttpServletResponse对象返回错误的JSON数据
            return;
        }
        //6.放行。
        log.info("令牌合法，直接放行");
        filterChain.doFilter(servletRequest,servletResponse);

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
