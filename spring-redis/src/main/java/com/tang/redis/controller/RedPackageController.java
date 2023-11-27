package com.tang.redis.controller;

import com.tang.redis.service.RedPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class RedPackageController {

    @Autowired
    private RedPackageService redPackageService;

    @ApiOperation("发送红包测试")
    @PostMapping("/send_package")
    public String sendPackage(int money,int num){
        return redPackageService.sendPackage(money,num);
    }

    @ApiOperation("抢占红包测试")
    @GetMapping("/rob_package")
    public String robPackage(String redPackageKey,String userId){
        return redPackageService.robPackage(redPackageKey, userId);
    }
}
