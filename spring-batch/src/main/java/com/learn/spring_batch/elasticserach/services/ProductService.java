package com.learn.spring_batch.elasticserach.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring_batch.elasticserach.models.dto.Hit;
import com.learn.spring_batch.elasticserach.models.request.GetProductPageReq;
import com.learn.spring_batch.elasticserach.models.response.CustomResponse;
import com.learn.spring_batch.elasticserach.models.response.ElasticsearchResponse;
import com.learn.spring_batch.elasticserach.models.response.ProductResponse;
import com.learn.spring_batch.elasticserach.productfeignclient.ProductElasticSearchFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            log.info("data===================== : {}", data);
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
        return CustomResponse.success(prepateProductResponse(resposne), "product get successfully", HttpStatus.OK);
    }


    public CustomResponse getProductsBasedOnPriceRange(double minPrice, double maxPrice) {

        try {
            // Construct Query
            Map<String, Object> rangeQuery = new HashMap<>();
            rangeQuery.put("gte", minPrice);
            rangeQuery.put("lte", maxPrice);

            Map<String, Object> price = new HashMap<>();
            price.put("price", rangeQuery);

            Map<String, Object> range = new HashMap<>();
            range.put("range", price);

            Map<String, Object> query = new HashMap<>();
            query.put("query", range);

            // Convert Java Object to JSON String
            String jsonQuery = objectMapper.writeValueAsString(query);

            log.info("json query : {}", jsonQuery);

            // Call Feign Client
            ElasticsearchResponse products = this.productElasticSearchFeignClient.getProductsBasedOnPriceRange("products", jsonQuery);

          return  CustomResponse.success(prepateProductResponse(products),"product get successfully", HttpStatus.OK);

        } catch (Exception e) {
            log.error("error message : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public CustomResponse getProductBasedOnPageSize(GetProductPageReq getProductPageReq) {

        int from = (getProductPageReq.getPageNo()-1) * getProductPageReq.getPageSize();


        Map<String, Object> query;

        if (getProductPageReq.getSearchText() != null && !getProductPageReq.getSearchText().trim().isEmpty()) {
            // Fuzzy search query
            Map<String, Object> matchQuery = new HashMap<>();
            matchQuery.put("query", getProductPageReq.getSearchText());
            matchQuery.put("fuzziness", "AUTO"); // Enables typo tolerance

            query = Map.of("match", Map.of("name", matchQuery));
        } else {
            // Match all if no search text is provided
            query = Map.of("match_all", Map.of());
        }

// Construct request body
        Map<String, Object> request = Map.of(
                "from", from,
                "size", getProductPageReq.getPageSize(),
                "query", query
        );
        try {
            String  string = objectMapper.writeValueAsString(request);

            log.info("data with fuzzy ================= {}", string);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ElasticsearchResponse response = this.productElasticSearchFeignClient.searchProducts(request);

        List<ProductResponse> productResponses = prepateProductResponse(response);
        log.info("productResponse : {]", productResponses);

       return  CustomResponse.success(productResponses, "product get successfully",HttpStatus.OK);

    }

    public CustomResponse getProductCount() {
        int count = (int) this.productElasticSearchFeignClient.getProductCount().get("count");
       return  CustomResponse.success(Map.of("productCount ", count),"count get sucessfully", HttpStatus.OK);
    }
}

