package com.learn.spring_batch.elasticserach.models.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private double price;

}
