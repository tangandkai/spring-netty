package com.tang.blog.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class JSONController {

    @RequestMapping(value = "/json",method = {RequestMethod.GET,RequestMethod.POST},produces = {})
    public String json(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject);
        String s = JSONObject.toJSONString(jsonObject);
        System.out.println(s);
        return s;
    }

    @PostMapping("/json_1")
    public String json_1(HttpServletRequest request) throws IOException {
        int len = request.getContentLength();
        ServletInputStream in = request.getInputStream();
        byte[] buffer = new byte[len];
        in.read(buffer, 0, len);
        String s = new String(buffer);
        System.out.println(s);
        return s;
    }
}
