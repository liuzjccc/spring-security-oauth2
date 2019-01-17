package com.liuzj.oauth2server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "test/hello")
    public String hello(){
        return "hello world!";
    }

    @GetMapping(value = "decision/hello")
    public String decision(){
        return "decision";
    }

    @GetMapping(value = "admin/hello")
    public String admin(){
        return "admin";
    }

    @GetMapping(value = "/")
    public String home(){
        return "home";
    }

    @RequestMapping("/login")
    public String login(String username, String password) {
        System.out.println("username=" + username + ",password=" + password);
        return "登录页面";
    }

}
