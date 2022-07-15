package com.tang.sureness.controller;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@RestController
public class SimulateController {
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource success";

    @GetMapping("/api/v1/source1")
    public ResponseEntity<String> api1Mock1() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @PutMapping("/api/v1/source1")
    public ResponseEntity<String> api1Mock3() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @DeleteMapping("/api/v1/source1")
    public ResponseEntity<String> api1Mock4() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/api/v1/source2")
    public ResponseEntity<String> api1Mock5() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/api/v1/source2/{var1}/{var2}")
    public ResponseEntity<String> api1Mock6(@PathVariable String var1, @PathVariable Integer var2 ) {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @PostMapping("/api/v2/source3/{var1}")
    public ResponseEntity<String> api1Mock7(@PathVariable String var1) {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/api/v1/source3")
    public ResponseEntity<String> api1Mock11(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(9000);
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

//    @PostMapping("/api/v1/php")
//    public ResponseEntity<String> php() throws InterruptedException {
//        Thread.sleep(9000);
//        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
//    }

    @PostMapping("/api/v1/php")
    public String php_() throws InterruptedException {
        Thread.sleep(4000);
        String string = JSONObject.toJSONString(new PHP(), true);
        return string;
    }
}

class PHP implements Serializable {

    private Header header;
    private Body body;


    public PHP(){
        header = new Header();
        body = new Body();
        body.setData(Arrays.asList("23","54","67","32"));
    }

    public Header getHeader() {
        return header;
    }


    public Body getBody() {
        return body;
    }

    static class Header{
        int code = 200;
        String desc = "success";

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    static class Body{
        List data;
        int size = 1;
        public void setData(List data) {
            this.data = data;
        }

        public List getData() {
            return data;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}