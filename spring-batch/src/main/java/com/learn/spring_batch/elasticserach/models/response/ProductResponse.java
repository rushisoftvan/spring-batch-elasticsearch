package com.learn.spring_batch.elasticserach.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private String name;
    private String description;
    private double price;

}
