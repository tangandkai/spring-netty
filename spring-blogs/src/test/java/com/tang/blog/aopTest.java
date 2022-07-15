package com.tang.blog;

import com.tang.blog.service.Student;
import org.apache.ibatis.plugin.Intercepts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class aopTest {

    @Resource
    private Student student;
    @Test
    public void test_1(){
        System.out.println(student.getAge()+"------------------");;
    }
}
