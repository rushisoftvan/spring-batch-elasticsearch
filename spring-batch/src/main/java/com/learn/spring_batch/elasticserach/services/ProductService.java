package com.learn.spring_batch.elasticserach.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring_batch.elasticserach.models.dto.Hit;
import com.learn.spring_batch.elasticserach.models.response.CustomResponse;
import com.learn.spring_batch.elasticserach.models.response.ElasticsearchResponse;
import com.learn.spring_batch.elasticserach.models.response.ProductResponse;
import com.learn.spring_batch.elasticserach.productfeignclient.ProductElasticSearchFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductElasticSearchFeignClient productElasticSearchFeignClient;
    private final ObjectMapper objectMapper;

    public CustomResponse getProducts() {

        try {
            ElasticsearchResponse products = productElasticSearchFeignClient.getAllData("products");
            String data = objectMapper.writeValueAsString(products);
            log.info("data=====================", data);
            List<ProductResponse> productList = prepateProductResponse(products);
            return CustomResponse.success(productList, "product get successfully", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ProductResponse> prepateProductResponse(ElasticsearchResponse products) {
        return products.getHits().getHits()
                .stream()
                .map(Hit::getProductResponse)
                .collect(Collectors.toList());
    }

//    public CustomResponse addProduct(AddProductReq addProductReq) {
//
//        ElasticsearchResponse response = this.productElasticSearchFeignClient.addProduct(addProductReq);
//
//
//
//        return null;
//    }

    public CustomResponse getProductByName(String productName) {
        ElasticsearchResponse resposne = this.productElasticSearchFeignClient.searchByName("products", productName);
        return CustomResponse.success(prepateProductResponse(resposne), "product get succesfully", HttpStatus.OK);

    }
}
