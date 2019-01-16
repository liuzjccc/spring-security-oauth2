package com.liuzj.resource.controller;

import com.liuzj.resource.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/decision/userinfo")
    public ResponseEntity<User> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext() .getAuthentication();
        String userName = (String) SecurityContextHolder.getContext() .getAuthentication().getPrincipal();
        User user = new User();
        user.setName(userName);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/admin/userinfo")
    public String admin() {
        return "admin";
    }
}
