package com.learn.spring_batch.elasticserach.productfeignclient;


import com.learn.spring_batch.elasticserach.models.request.AddProductReq;
import com.learn.spring_batch.elasticserach.models.response.ElasticsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "elasticsearchClient", url = "http://localhost:9200") // Replace with your Elasticsearch URL
public interface ProductElasticSearchFeignClient {


    @GetMapping("/{index}/_search")
    ElasticsearchResponse getAllData(@PathVariable("index") String index);

//    @PostMapping("/products/_doc")
//    ElasticsearchResponse addProduct(@RequestBody AddProductReq addProductReq);

    @GetMapping("/{index}/_search")
    ElasticsearchResponse searchByName(@PathVariable("index") String index, @RequestParam("q") String query);

}
