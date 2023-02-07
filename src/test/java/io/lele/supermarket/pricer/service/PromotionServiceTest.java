package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.model.*;
import io.lele.supermarket.pricer.model.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.model.enums.PromotionBase;
import io.lele.supermarket.pricer.model.enums.PromotionEvaluationType;
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
        Promotion promotion = new Promotion(PromotionEvaluationType.Quantity, null, new BigDecimal(3), PromotionBase.Quantity, new BigDecimal(1));
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
    void evaluateNullPromotionProductWithFixPrice45USD() throws IncompatibleUnitsException {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.Quantity, null, new BigDecimal(3), PromotionBase.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        assertAll( () -> assertEquals(new BigDecimal(0), item.getOfferedQuantity(), "Offered quantity unchanged"),
                () -> assertEquals(new BigDecimal(3), item.getTotalQuantity(), "Total quantity unchanged"),
                () -> assertEquals(new BigDecimal(45), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(new BigDecimal(45), item.getTotalPrice(), "Total price unchanged"));

    }

    @Test
    @DisplayName("Eval BasketItem - Null Promotion type - exp InvalidProductPromotion")
    void evaluateNullPromotionTypeProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(null, null, new BigDecimal(3), PromotionBase.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertThrows(InvalidProductPromotion.class, () -> promotionService.evaluateProductPromotion(item));
    }

    @Test
    @DisplayName("Eval Item - Null Promotion Qty - exp InvalidProductPromotion")
    void evaluateNullPromotionQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.Quantity, null, null, PromotionBase.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertThrows(InvalidProductPromotion.class, () -> promotionService.evaluateProductPromotion(item));
    }

    @Test
    @DisplayName("Eval Item - Null Promo Qty- Flat Amt- exp InvalidProductPromotion")
    void evaluateNullPromotionOfferedQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.Quantity, null, new BigDecimal(3), PromotionBase.Quantity, null);
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertThrows(InvalidProductPromotion.class, () -> promotionService.evaluateProductPromotion(item));
    }

    @Test
    @DisplayName("Eval Item - Flat Amt- exp IncompatibleUnitsException")
    void evaluatePromotionIncompatibleConversion() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEvaluationType.Quantity, LengthUnitOfMeasurement.Meter, new BigDecimal(3), PromotionBase.UnitOfMeasurement, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertThrows(IncompatibleUnitsException.class, () -> promotionService.evaluateProductPromotion(item));

    }

}
