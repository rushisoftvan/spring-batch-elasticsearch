package com.learn.spring_batch.processer;

import com.learn.spring_batch.elasticserach.entities.ProductEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductItemProcessor implements ItemProcessor<ProductEntity,ProductEntity> {
    @Override
    public ProductEntity process(ProductEntity item) throws Exception {
        return item;
    }
}
