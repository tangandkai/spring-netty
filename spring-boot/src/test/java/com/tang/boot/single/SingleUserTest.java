package com.tang.boot.single;

import com.tang.boot.prototype.ProtoUser;
import com.tang.boot.utils.SpringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SingleUserTest {


    @Test
    void getProtoUser() {
        new Thread(()->{
            SingleUser singleUser = SpringUtil.getBean("singleUser",SingleUser.class);
            ProtoUser protoUser = singleUser.getProtoUser();
            protoUser.setValue(11);
            int value = protoUser.getValue();
            System.out.println(value);
            System.out.println(protoUser);
        }).start();

        new Thread(()->{
            SingleUser singleUser = SpringUtil.getBean("singleUser",SingleUser.class);
            ProtoUser protoUser = singleUser.getProtoUser();
            protoUser.setValue(19);
            int value = protoUser.getValue();
            System.out.println(value);
            System.out.println(protoUser);
        }).start();
    }
}