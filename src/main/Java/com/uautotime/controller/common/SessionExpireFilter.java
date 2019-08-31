package com.uautotime.controller.common;

import com.uautotime.common.Const;
import com.uautotime.pojo.User;
import com.uautotime.util.CookieUtil;
import com.uautotime.util.JsonUtil;
import com.uautotime.util.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by yaosheng on 2019/8/30.
 */
public class SessionExpireFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isNotEmpty(loginToken)){
            //判断loginToken是否为空或者" "；
            //如果不为空，即符合条件，继续拿user信息

            String userJsonStr = RedisPoolUtil.get(loginToken);
            User user = JsonUtil.string2obj(userJsonStr,User.class);
            if(user != null){
                //如果user不为空，则重置session的时间，即调用expire命令
                RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

}
