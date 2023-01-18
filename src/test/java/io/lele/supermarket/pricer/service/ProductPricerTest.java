package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.service.impl.ProductPricerImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductPricerTest {

    private static ProductPricer pricer;

    private static Product product;
    private static BasketItem item;

    @BeforeAll
    @DisplayName("Initialize products and pricer")
    static void init(){
        pricer = new ProductPricerImpl();
        product = new Product("Table ", new BigDecimal(15));
        item = new BasketItem(product);

    }

    @Test
    @DisplayName("Fixed Amount - Should return 45 for 3 products USD15")
    void evaluateProductWithFixPrice45USD(){
        item.setQuantity(new BigDecimal(3));
        pricer.evaluatePrice(item);
        assertEquals(new BigDecimal(45), item.getPrice());
    }








}