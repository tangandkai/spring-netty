package com.tang.es.dao;


import com.tang.es.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品操作
 */

@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long> {


}
