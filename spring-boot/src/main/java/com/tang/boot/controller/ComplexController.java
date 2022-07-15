package com.tang.boot.controller;

import com.tang.boot.beans.complex.Params;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义复杂对象测试
 * @author tangwenkai
 * @date 2021-06-22 20:56
 */
@Api
@RestController
public class ComplexController {

    @ApiOperation("复杂对象测试")
    @PostMapping("/complex_1")
    public Params complex_1(@RequestBody Params params){
        System.out.println(params);
        return params;
    }
}
