package com.tang.es.config;

import com.tang.es.dao.ProductDao;
import com.tang.es.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringDataElasticDocTest {

    @Autowired
    private ProductDao productDao;

    @Test
    void saveTest(){

        Product product = new Product();
        product.setId(2L);
        product.setPrice(23.89);
        product.setTitle("牛肉干");
        product.setCategory("肉干");
        product.setImages("http/wwww.baidu.com/beef");
        try {
            productDao.save(product);
        }catch (Exception e){

        }

        System.out.println("添加数据 ......................");
    }
}
