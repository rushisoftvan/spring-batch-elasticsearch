package com.learn.spring_batch.elasticserach.productfeignclient;


import com.learn.spring_batch.elasticserach.models.response.ElasticsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "elasticsearchClient", url = "http://localhost:9200") // Replace with your Elasticsearch URL
public interface ProductElasticSearchFeignClient {


    @GetMapping("/{index}/_search")
    ElasticsearchResponse getAllData(@PathVariable("index") String index);



    @GetMapping("/{index}/_search")
    ElasticsearchResponse searchByName(@PathVariable("index") String index, @RequestParam("q") String query);

    @PostMapping(value = "/{index}/_search", consumes = "application/json", produces = "application/json")
    ElasticsearchResponse getProductsBasedOnPriceRange(@PathVariable("index") String index, @RequestBody String query);



    @PostMapping("/products/_search")
    ElasticsearchResponse searchProducts(@RequestBody Map<String, Object> request);

    @GetMapping("/products/_count")
    Map<String, Object> getProductCount();

}
