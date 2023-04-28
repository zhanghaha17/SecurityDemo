package com.example.security.controller;

import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    @GetMapping("/index")
    @ResponseBody
    public Object hello(Authentication authentication){
        //return "index";
        return authentication;
    }

    @GetMapping("/queryUsers")
    public List<Map<String,Object>> queryUsers(){
        return userService.queryAllUser();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Spring Boot Test Demo!";
    }

    @GetMapping("/signout/success")
    @ResponseBody
    public String logout(){
        return "退出成功，请重新登录";
    }

    @GetMapping("/auth/admin")
    @PreAuthorize("hasAuthority(admin)")
    @ResponseBody
    public String authenticationTest() {
        return "您拥有admin权限，可以查看";
    }
}
