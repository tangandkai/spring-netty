package com.tang.shard.controller;

import com.tang.shard.repository.UserRepository;
import com.tang.shard.service.HealthUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
public class HealthUserController {

    @Resource
    private HealthUserService userService;
//
    @Resource
    private UserRepository userRepository;

    @GetMapping("/create/users")
    public String testProcessUsers() throws SQLException, InterruptedException {
        userService.processUsers();
        Thread.sleep(1000);
        return "ok......"+"\t"+userService.getUsers();
    }

    @GetMapping("/deleteAll/users")
    public String delProcessUsers() throws SQLException, InterruptedException {
        userService.deleteAll();
        Thread.sleep(1000);
        return "ok......"+"\t"+userService.getUsers();
    }
}
