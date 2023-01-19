package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.enums.PricingType;
import io.lele.supermarket.pricer.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.service.impl.ProductPricerImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

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
        try {
            pricer.evaluatePrice(item);
            assertEquals(new BigDecimal(45), item.getPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 45, Qty = 3, UP=15")
    void evaluateBasket45USDOf3ItemsOf15USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(15));
        BasketItem item  = new BasketItem(product, new BigDecimal(3));
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(45), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 0, UP=15")
    void evaluateBasket45USDOf0temsOf15USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(15));
        BasketItem item  = new BasketItem(product, new BigDecimal(0));
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(0), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 3, UP=0")
    void evaluateBasket45USDOf3ItemsOf0USDFixedPrice(){
        Product   product = new Product("Table ", new BigDecimal(0));
        BasketItem item  = new BasketItem(product, new BigDecimal(3));
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(0), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Eval Basket -three-for-one-dollar - exp 2, Qty = 6, UP=1")
    void evaluateBasketTreeForOneDollar(){
        Product   product = new Product("Table ", new BigDecimal(1));
        product.setPricedQuantity(new BigDecimal(3) );
        product.setPricingType(PricingType.PriceOnQuantity);
        BasketItem item  = new BasketItem(product, new BigDecimal(6));
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(2), basket.getTotalPrice().round(new MathContext(1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Eval Basket - Length pricing - exp 18, Qty = 6m, UP=3")
    void evaluateBasket6mOfTissuePriced3USDPerMetter(){
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        BasketItem item  = new BasketItem(product, new BigDecimal(6), PhysicalQuantity.Length, LengthUnitOfMeasurement.Meter);
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(18), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Eval Basket - Length pricing - exp 1.2, Qty = 40cm, UP=3")
    void evaluateBasket40cmOfTissuePriced3USDPerMetter(){
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        BasketItem item  = new BasketItem(product, new BigDecimal(40), PhysicalQuantity.Length, LengthUnitOfMeasurement.Centimeter);
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(1.2, new MathContext(2)), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Eval Basket - Length pricing - exp 1500, Qty = 0.5km, UP=3")
    void evaluateBasketHalfkmOfTissuePriced3USDPerMetter(){
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        BasketItem item  = new BasketItem(product, new BigDecimal(0.5, new MathContext(0)), PhysicalQuantity.Length, LengthUnitOfMeasurement.Kilometer);
        Basket basket = new Basket();
        basket.getItems().add(item);
        try {
            pricer.evaluateBasket(basket);
            assertEquals(new BigDecimal(1500), basket.getTotalPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}