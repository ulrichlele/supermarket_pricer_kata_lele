package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.model.Basket;
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


    @BeforeAll
    @DisplayName("Initialize products and pricer")
    static void init(){
        pricer = new ProductPricerImpl();
    }

    @Test
    @DisplayName("Eval BasketItem - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluateProductWithFixPrice45USD(){
        Product   product = new Product("Table ", new BigDecimal(15));
        BasketItem item  = new BasketItem(product, new BigDecimal(3));
        pricer.evaluatePrice(item);
        assertEquals(new BigDecimal(45), item.getPrice());
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 45, Qty = 3, UP=15")
    void evaluateBasket45USDOf3ItemsOf15USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(15));
        BasketItem item  = new BasketItem(product, new BigDecimal(3));
        Basket basket = new Basket();
        basket.getItems().add(item);
        pricer.evaluateBasket(basket);
        assertEquals(new BigDecimal(45), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 0, UP=15")
    void evaluateBasket45USDOf0temsOf15USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(15));
        BasketItem item  = new BasketItem(product, new BigDecimal(0));
        Basket basket = new Basket();
        basket.getItems().add(item);
        pricer.evaluateBasket(basket);
        assertEquals(new BigDecimal(0), basket.getTotalPrice());
    }
    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 3, UP=0")
    void evaluateBasket45USDOf3temsOf0USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(0));
        BasketItem item  = new BasketItem(product, new BigDecimal(3));
        Basket basket = new Basket();
        basket.getItems().add(item);
        pricer.evaluateBasket(basket);
        assertEquals(new BigDecimal(0), basket.getTotalPrice());
    }





}