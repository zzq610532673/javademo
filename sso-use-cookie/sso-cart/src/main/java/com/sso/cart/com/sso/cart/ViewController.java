package com.sso.cart.com.sso.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private RestTemplate restTemplate;

    private final String USER_LOGIN_URL = "http://login.codeshop.com:9000/login/info?token=";
    @GetMapping("/index")
    public String tiIndex(@CookieValue(required = false,value = "TOKEN")Cookie cookie,
                          HttpSession session){
        if (cookie !=null){
            String token = cookie.getValue();
            if (!StringUtils.isEmpty(token)){
                Map result = restTemplate.getForObject(USER_LOGIN_URL + token, Map.class);
                session.setAttribute("loginUser",result);
            }
        }
        return "index";
    }

}
