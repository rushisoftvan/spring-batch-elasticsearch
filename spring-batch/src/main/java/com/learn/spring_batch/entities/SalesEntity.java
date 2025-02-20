package com.learn.spring_batch.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="sales")
@NoArgsConstructor
@AllArgsConstructor
public class SalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name="region")
    private String region;

    @Column(name="country")
    private String country;

    @Column(name="item_type")
    private String itemType;

    @Column(name="sales_channel")
    private String salesChannel;

    @Column(name="order_priority")
    private String orderPriority;


    @Column(name="order_date")
    private LocalDate orderDate;

    @Column(name="order_id")
    private String orderId;

    @Column(name="shipe_date")
    private LocalDate shipDate;

    @Column(name="unit_solid")
    private  Long unitsSold;

    @Column(name="unit_price")
    private Double unitPrice;

    @Column(name="unit_cost")
    private Double unitCost;

    @Column(name="total_revenue")
    private Double totalRevenue;

    @Column(name="total_cost")
    private Double totalCost;

    @Column(name="total_profit")
    private Double totalProfit;


}
