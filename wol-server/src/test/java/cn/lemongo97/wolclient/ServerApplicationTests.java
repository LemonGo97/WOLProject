package cn.lemongo97.wolclient;

import cn.lemongo97.wol.ServerApplication;
import cn.lemongo97.wol.service.UserService;
import cn.lemongo97.wol.ws.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() throws IOException, InterruptedException {
        System.out.println(userService.loadUserByUsername("admin"));
    }

}
