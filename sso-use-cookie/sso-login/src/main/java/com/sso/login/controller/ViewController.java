package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转的逻辑
 */
@Controller
@RequestMapping("/view")
public class ViewController {
    /**
     * 跳转登陆页面
     * @return
     */
    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "")String target,
                          HttpSession session, @CookieValue(required = false,value = "TOKEN")Cookie cookie){
        if (StringUtils.isEmpty(target)){
            target = "http://www.codeshop.com:9010";
        }
        if (cookie != null){
            String token = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(token);
            if (user != null){
                return "redirect:"+target;
            }
        }
        session.setAttribute("target",target);
        return "login";
    }

}
