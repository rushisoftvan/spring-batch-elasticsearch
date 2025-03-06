package com.learn.spring_batch.elasticserach.productfeignclient;


import com.learn.spring_batch.elasticserach.models.response.ElasticsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "elasticsearchClient", url = "http://localhost:9200") // Replace with your Elasticsearch URL
public interface ProductElasticSearchFeignClient {


    @GetMapping("/{index}/_search")
    ElasticsearchResponse getAllData(@PathVariable("index") String index);


}
