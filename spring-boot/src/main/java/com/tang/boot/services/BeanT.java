package com.tang.boot.services;

import com.tang.boot.utils.SpringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BeanT {

    public void getBean(String name){
        Object bean = SpringUtil.getBean(name);
    }
}
