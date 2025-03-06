package com.learn.spring_batch.elasticserach.repositories;

import com.learn.spring_batch.elasticserach.entities.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepo extends ElasticsearchRepository<ProductEntity,Long> {

}
