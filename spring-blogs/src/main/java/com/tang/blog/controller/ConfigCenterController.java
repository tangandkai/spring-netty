package com.tang.blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConfigCenterController {

    @Value("${spring.datasource.type}")
    private String TYPE;

    @Value("${server.port}")
    private String PORT;

    @GetMapping("/type")
    public String getTYPE(){
        return TYPE+"\t"+PORT+"\t"+ UUID.randomUUID().toString();
    }
}
