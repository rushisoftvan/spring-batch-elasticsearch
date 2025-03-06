package com.learn.spring_batch.elasticserach.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learn.spring_batch.elasticserach.models.dto.Hits;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ElasticsearchResponse {


     private Hits hits;



}
