package io.lele.supermarket.pricer.application.service;

import io.lele.supermarket.pricer.application.services.BasketItemService;
import io.lele.supermarket.pricer.application.services.PromotionService;
import io.lele.supermarket.pricer.domain.Basket;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.enums.*;
import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class PromotionServiceTest {

    @Autowired
    private BasketItemService basketItemService;
    @Autowired
    private PromotionService promotionService;

    @Test
    @DisplayName("Eval Item - Promotion - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluatePromotionProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(1), item.getOfferedQuantity(), "One product offered"),
                () -> assertEquals(new BigDecimal(4), item.getTotalQuantity(), "Total quantity increased by one"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getTotalPrice(), "Total price unchanged after promotion"));

    }

    @Test
    @DisplayName("Eval BasketItem - Null Promotion - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluateNullPromotionProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(null);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(0), item.getOfferedQuantity(), "Offered quantity unchanged"),
                () -> assertEquals(new BigDecimal(3), item.getTotalQuantity(), "Total quantity unchanged"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getTotalPrice(), "Total price unchanged"));
    }

    @Test
    @DisplayName("Eval Item - Null Promo type throws InvalidProductPromotion")
    void evaluateNullPromotionTypeProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        assertThrows(InvalidProductPromotion.class, () -> new Promotion(null,  new BigDecimal(3), PromotionOfferType.Quantity, new BigDecimal(1)));
    }

    @Test
    @DisplayName("Eval Item - Null Promo Min Qty - exp 1 offered")
    void evaluateNullPromotionQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity, null,  PromotionOfferType.Quantity, new BigDecimal(1));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(1), item.getOfferedQuantity(), "One product offered"),
                () -> assertEquals(new BigDecimal(4), item.getTotalQuantity(), "Total quantity increased by one"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getPrice(), "Price is 45 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getTotalPrice(), "Total price unchanged after promotion"));
    }

    @Test
    @DisplayName("Eval Item - Null Promo Offered Qty- Flat Amt- throws InvalidProductPromotion")
    void evaluateNullPromotionOfferedQtyProductWithFixPrice45USD() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Table ", new BigDecimal(15));
        assertThrows(InvalidProductPromotion.class, () -> new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(3), PromotionOfferType.Quantity, null));
    }

    //Involves units of measurements
    @Test
    @DisplayName("Ten meters bought two meters offered")
    void evaluateTenMetersBoughtTwoMetersOffered() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Tissue", new BigDecimal(10), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter, PhysicalQuantity.Length);
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(10), PromotionOfferType.Quantity, new BigDecimal(2));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(14),  LengthUnitOfMeasurement.Meter);
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(2), item.getOfferedQuantity(), "Two meters offered"),
                () -> assertEquals(new BigDecimal(16), item.getTotalQuantity(), "Total quantity increased by 2 meters"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getPrice(), "Price is 140 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getTotalPrice(), "Total price unchanged after promotion"));
    }

    @Test
    @DisplayName("1400 centimeters bought two meters offered")
    void evaluate1400CentimetersBoughtTwoMetersOffered() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Tissue", new BigDecimal(10), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter, PhysicalQuantity.Length);
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(10), PromotionOfferType.Quantity, new BigDecimal(2));
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(1400),  LengthUnitOfMeasurement.Centimeter);
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(200).doubleValue(), item.getOfferedQuantity().doubleValue(), "Two meters offered"),
                () -> assertEquals(new BigDecimal(1600).doubleValue(), item.getTotalQuantity().doubleValue(), "Total quantity increased by 200 centimeters"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getPrice(), "Price is 140 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getTotalPrice(), "Total price unchanged after promotion"));
    }



    @Test
    @DisplayName("1400 centimeters bought 10% discount")
    void evaluate1400CentimetersBought10PercentDiscount() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Cable", new BigDecimal(10), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter, PhysicalQuantity.Length);
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(10), PromotionOfferType.PriceReduction, new BigDecimal(10), PriceReductionType.Percentage);
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(1400),  LengthUnitOfMeasurement.Centimeter);
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(0), item.getOfferedQuantity(), "0 centimeters offered"),
                () -> assertEquals(new BigDecimal(1400), item.getTotalQuantity(), "Total quantity unchanged"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getPrice(), "Price is 140 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(126)), item.getTotalPrice(), "Total price = 10% discount on 140"));
    }


    @Test
    @DisplayName("1400 centimeters bought 20USD discount")
    void evaluate1400CentimetersBought20USDDiscount() throws IncompatibleUnitsException, InvalidProductPromotion {
        Product product = new Product("Cable", new BigDecimal(10), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter, PhysicalQuantity.Length);
        Promotion promotion = new Promotion(PromotionEligibilityBase.PurchaseQuantity,  new BigDecimal(10), PromotionOfferType.PriceReduction, new BigDecimal(20), PriceReductionType.Flat);
        product.setPromotion(promotion);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(1400),  LengthUnitOfMeasurement.Centimeter);
        basketItemService.evaluatePrice(item);
        promotionService.evaluateProductPromotion(item);
        assertAll( () -> assertEquals(new BigDecimal(0), item.getOfferedQuantity(), "0 centimeters offered"),
                () -> assertEquals(new BigDecimal(1400), item.getTotalQuantity(), "Total quantity unchanged"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(140)), item.getPrice(), "Price is 140 (without promotion)"),
                () -> assertEquals(AmountUtil.scaleAmount(new BigDecimal(120)), item.getTotalPrice(), "Total price = 10% discount on 140"));
    }
}
