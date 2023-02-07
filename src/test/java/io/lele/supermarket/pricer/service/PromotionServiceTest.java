package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.model.*;
import io.lele.supermarket.pricer.model.enums.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionServiceTest {

    private static ProductService productService;

    private static PromotionService promotionService;
    @BeforeAll
    @DisplayName("Initialize products and pricer")
    static void init(){
        productService = new ProductService();
        promotionService = new PromotionService();
    }

    @Test
    @DisplayName("Eval Item - Promotion - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluatePromotionProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.PurchasedQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(1), item.getOfferedQuantity(), "One product offered"),
                () -> assertEquals(new BigDecimal(4), item.getTotalQuantity(), "Total quantity increased by one"),
                () -> assertEquals(new BigDecimal(45), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(new BigDecimal(45), item.getTotalPrice(), "Total price unchanged after promotion"));

    }

    @Test
    @DisplayName("Eval BasketItem - Null Promotion - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluateNullPromotionProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.PurchasedQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(null);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(0), item.getOfferedQuantity(), "Offered quantity unchanged"),
                () -> assertEquals(new BigDecimal(3), item.getTotalQuantity(), "Total quantity unchanged"),
                () -> assertEquals(new BigDecimal(45), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(new BigDecimal(45), item.getTotalPrice(), "Total price unchanged"));
    }

    @Test
    @DisplayName("Eval Item - Null Promo type throws InvalidProductPromotion")
    void evaluateNullPromotionTypeProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(null,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        assertThrows(InvalidProductPromotion.class, () -> promotionService.evaluateProductPromotion(item));
    }

    @Test
    @DisplayName("Eval Item - Null Promo Min Qty - exp 1 offered")
    void evaluateNullPromotionQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.PurchasedQuantity, null,  PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(1), item.getOfferedQuantity(), "One product offered"),
                () -> assertEquals(new BigDecimal(4), item.getTotalQuantity(), "Total quantity increased by one"),
                () -> assertEquals(new BigDecimal(45), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(new BigDecimal(45), item.getTotalPrice(), "Total price unchanged after promotion"));
    }

    @Test
    @DisplayName("Eval Item - Null Promo Offered Qty- Flat Amt- throws InvalidProductPromotion")
    void evaluateNullPromotionOfferedQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.PurchasedQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, null);
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        assertThrows(InvalidProductPromotion.class, () -> promotionService.evaluateProductPromotion(item));
    }


    //Involves units of measurements
    @Test
    @DisplayName("Ten meters bought two meters offered")
    void evaluateTenMetersBoughtTwoMetersOffered() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Tissue", new BigDecimal(10), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        Promotion promotion = new Promotion(PromotionEvaluationType.PurchasedQuantity,  new BigDecimal(10), PromotionOfferType.Quantity, new BigDecimal(2));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(14), PhysicalQuantity.Length, LengthUnitOfMeasurement.Meter);
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(2), item.getOfferedQuantity(), "Two meters offered"),
                () -> assertEquals(new BigDecimal(16), item.getTotalQuantity(), "Total quantity increased by one"),
                () -> assertEquals(new BigDecimal(140), item.getPrice(), "Price is 140 (without promotion)"),
                () -> assertEquals(new BigDecimal(140), item.getTotalPrice(), "Total price unchanged after promotion"));
    }

}
