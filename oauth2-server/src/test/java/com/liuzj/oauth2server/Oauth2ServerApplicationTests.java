package com.liuzj.oauth2server;

import com.liuzj.oauth2server.domain.User;
import com.liuzj.oauth2server.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2ServerApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoads() {
        User users = userRepository.findByUserName("tank");
    }

}

