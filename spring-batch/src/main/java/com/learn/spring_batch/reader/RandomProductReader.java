package com.learn.spring_batch.reader;

import com.github.javafaker.Faker;
import com.learn.spring_batch.ProductCatagaryEnum;
import com.learn.spring_batch.elasticserach.entities.ProductDetail;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomProductReader implements ItemReader<ProductDetail> {

    private final Faker faker = new Faker();
    private final AtomicInteger count = new AtomicInteger(0);
    private final int maxProducts = 1000; // Generate 10 products
    private final Random random = new Random();

    @Override
    public ProductDetail read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        ProductCatagaryEnum[] categories = ProductCatagaryEnum.values();

        if (count.getAndIncrement() >= maxProducts) {
            return null; // Stop reading
        }

        return  new ProductDetail(
            faker.commerce().productName(),
            categories[random.nextInt(categories.length)],
                Double.parseDouble(faker.commerce().price())
        );


        return null;
    }
}
