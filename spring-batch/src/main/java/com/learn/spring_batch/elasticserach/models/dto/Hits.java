package com.learn.spring_batch.elasticserach.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Hits {

    List<Hit> hits;

}
