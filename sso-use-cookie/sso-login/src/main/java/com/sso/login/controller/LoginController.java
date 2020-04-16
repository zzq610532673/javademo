package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Set<User> dbUsers;
    static{
        dbUsers = new HashSet<>();
        dbUsers.add(new User(0,"zzq","zzq123"));
        dbUsers.add(new User(1,"zjj","zjj123"));
        dbUsers.add(new User(2,"wl","wl123"));
        dbUsers.add(new User(3,"sxd","sxd123"));
    }
    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response){
        String target = (String) session.getAttribute("target");
        //模拟从数据库中通过登录名和密码查找数据库中的用户
        Optional<User> first = dbUsers.stream().filter(dbUsers -> dbUsers.getUsername().equals(user.getUsername()) &&
                dbUsers.getPassword().equals(user.getPassword()))
                .findFirst();
        if(first.isPresent()){
            //保存用户信息
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setDomain("codeshop.com");
            response.addCookie(cookie);
            LoginCacheUtil.loginUser.put(token,first.get());
            System.out.println(first.get().toString());
            return "redirect:"+target;
        }else {
            //登录失败
            session.setAttribute("msg","用户名或密码错误");
            return "login";
        }

    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if (!StringUtils.isEmpty(token)){
            System.out.println(token+"zzqinfo");
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }else{
            return new ResponseEntity<>((User) null, HttpStatus.BAD_REQUEST);
        }
    }
}
