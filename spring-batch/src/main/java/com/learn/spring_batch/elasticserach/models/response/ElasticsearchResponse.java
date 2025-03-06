package com.learn.spring_batch.elasticserach.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learn.spring_batch.elasticserach.models.dto.Hits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticsearchResponse {


     private Hits hits;



}
