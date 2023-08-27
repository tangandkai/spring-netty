package com.tang.es.config;

import com.tang.es.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringDataElasticTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void createIndexTest(){
        System.out.println("创建索引 ......................");
    }


    @Test
    void deleteIndexTest(){
        boolean deleteIndex = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引 结果："+deleteIndex);
    }
}
