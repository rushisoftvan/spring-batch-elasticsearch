package com.learn.spring_batch.elasticserach.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learn.spring_batch.elasticserach.models.response.ProductResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Hit {


    @JsonProperty("_id")
    private String id;  // Extract the _id from Elasticsearch response


    @JsonProperty("_source")
    private ProductResponse productResponse;

    // Setter to map _id into productResponse
    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
        if (this.productResponse != null) {
            this.productResponse.setId(this.id);  // Assign _id to ProductResponse
        }
    }

}
