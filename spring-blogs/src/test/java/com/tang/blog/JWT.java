package com.tang.blog;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

@SpringBootTest
public class JWT {

    @Test
    public void test_1(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println("key:{}="+key.toString());
        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        System.out.println("jws:{}="+jws);
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();
        System.out.println("subject:{}="+subject);
    }
}
