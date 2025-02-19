package com.learn.spring_batch.processer;

import com.learn.spring_batch.entities.SalesEntity;
import org.springframework.batch.item.ItemProcessor;

public class SalesItemProcessor implements ItemProcessor<SalesEntity, SalesEntity> {


    @Override
    public SalesEntity process(SalesEntity item) throws Exception {
        return item;
    }
}
