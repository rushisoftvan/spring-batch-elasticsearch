package com.learn.spring_batch.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {


    private String region;


    private String country;


    private String itemType;


    private String salesChannel;


    private String orderPriority;



    private LocalDate orderDate;


    private String orderId;


    private LocalDate shipDate;


    private  Long unitsSold;


    private Double unitPrice;


    private Double unitCost;


    private Double totalRevenue;


    private Double totalCost;


    private Double totalProfit;

}
