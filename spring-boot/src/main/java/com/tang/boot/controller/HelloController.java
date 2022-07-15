package com.tang.boot.controller;


import com.tang.boot.beans.Users;
import com.tang.boot.mapper.entity.UserT;
import com.tang.boot.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Swagger测试样例
 * @author tangwenkai
 * @date 2021-06-03 12:38
 */
@RestController
@Api
public class HelloController {

    @Resource(name = "userServiceImpl_2")
    private UserService userService;

    @ApiOperation("Hello Spring Boot 方法")
    @GetMapping("/")
    public String hello(@RequestParam(required = false) @ApiParam("名字") String name) {
        if (name != null && !"".equals(name)) {
            return "Hello " + name;
        }
        return "Hello Spring Boot";
    }

    @ApiOperation("Hello Spring Boot 方法")
    @GetMapping("/user")
    public Users hello_2( @ApiParam("用户") Users users) {
        System.out.println("get===============");
        System.out.println(users);
        return users;
    }

    @ApiOperation("Hello Spring Boot 方法")
    @PostMapping("/user")
    public Users hello_3( @RequestBody @ApiParam("用户") Users users) {
        System.out.println("post===============");
        System.out.println(users);
        return users;
    }

    @ApiOperation("Hello Spring Boot 方法")
    @GetMapping("/string")
    public String hello_4() {
        System.out.println("post===============");
        return UUID.randomUUID().toString();
    }

    @ApiOperation("Hello Spring Boot 方法")
    @GetMapping("/user/get")
    public UserT getUserByName(@RequestParam(required = false) @ApiParam("名字") String name){
        return userService.getUserByName(name);
    }

    @ApiOperation("Hello Spring Boot 方法")
    @PostMapping("/user/del")
    public Boolean delete(@RequestParam(required = false) @ApiParam("名字") String name){
        return userService.delete(name);
    }

    @ApiOperation("Hello Spring Boot 方法")
    @PostMapping("/user/save")
    public UserT saveOrUpdate(@RequestBody @ApiParam("用户") UserT user){
        return userService.saveOrUpdate(user);
    }
}
