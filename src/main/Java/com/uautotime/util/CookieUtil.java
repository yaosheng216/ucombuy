package com.uautotime.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yaosheng on 2019/8/28.
 */
@Slf4j
public class CookieUtil {

    private static final String COOKIE_DOMAIN = ".ucombuy.com";
    private static final String COOKIE_NAME = "ucombuy_login_token";

    public static String readLoginToken(HttpServletRequest request){

        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                log.info("read cookieName : {},cookieValue : {}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName : {},cookieValue : {}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain=".ucombuy.com"
    //a:a.ucombuy.com                    cookie:domain=A.ucombuy.com;path="/"
    //b:b.ucombuy.com                    cookie:domain=B.ucombuy.com;path="/"
    //c:c.ucombuy.com/test/cc            cookie:domain=C.ucombuy.com;path="/test/cc"
    //d:d.ucombuy.com/test/dd            cookie:domain=D.ucombuy.com;path="/test/dd"
    //e:e.ucombuy.com/test               cookie:domain=E.ucombuy.com;path="/test"

    public static void writeLoginToken(HttpServletResponse response, String token){

        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");                       //代表path设置在根目录
        ck.setHttpOnly(true);                  //防止脚本攻击带来的信息泄露风险

        //单位是秒
        //如果maxage不设置，cookie就不会写入硬盘，而是写在内存中，只在当前页面有效
        ck.setMaxAge(60*60*24*365);            //如果是-1，代表是永久
        log.info("write cookieName : {},cookieValue : {}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){

        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);                 //设置为0，代表删除此Cookie
                    log.info("del cookieName : {},cookieValue : {}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
