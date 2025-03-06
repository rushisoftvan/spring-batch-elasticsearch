//package com.learn.spring_batch.writer;
//
//import com.learn.spring_batch.elasticserach.entities.ProductEntity;
//import com.learn.spring_batch.elasticserach.repositories.ProductRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class ProductWriter implements ItemWriter<ProductEntity> {
//
//    private final ProductRepo productRepo;
//
//
//    @Override
//    public void write(Chunk<? extends ProductEntity> products) throws Exception {
//
//          productRepo.saveAll(products);
//    }
//}
