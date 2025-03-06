package com.learn.spring_batch.elasticserach.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddProductReq {

    private String name;
    private String description;
    private double price;

}
