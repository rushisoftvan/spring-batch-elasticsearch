package com.learn.spring_batch.elasticserach.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learn.spring_batch.elasticserach.models.response.ProductResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hit {

    @JsonProperty("_source")
    private ProductResponse productResponse;

}
