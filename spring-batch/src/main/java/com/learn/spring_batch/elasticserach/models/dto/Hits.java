package com.learn.spring_batch.elasticserach.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Hits {

    List<Hit> hits;

}
