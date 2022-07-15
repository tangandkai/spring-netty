package com.tang.security.controller;

import com.tang.security.beans.Response;
import com.tang.security.beans.WebStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class AccountController {

    @GetMapping("/response")
    public Response getResponse(){
        Response response = new Response();
        response.setMessage("nice to meet you");
        response.setWebStatus(WebStatus.OK);
        return response;
    }

    @GetMapping("/echo")
    public String echo(){
        return "echo test";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin test";
    }

    @GetMapping("/normal")
    public String normal(){
        return "normal test";
    }
}
