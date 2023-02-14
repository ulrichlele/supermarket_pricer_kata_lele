package io.lele.supermarket.pricer.application.service;

import io.lele.supermarket.pricer.application.services.BasketItemService;
import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.domain.Basket;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.enums.AreaUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BasketItemServiceTest {

    @Autowired
    private BasketItemService basketItemService;


    @Test
    @DisplayName("Eval BasketItem - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluateProductWithFixPrice45USD() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basketItemService.evaluatePrice(item);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getPrice());
    }

    @Test
    @DisplayName("Eval BasketItem - Throws EX - Length to Area")
    void evaluatePriceGeneratesIncompatibleUnitsException() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15), new BigDecimal(1), PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter, PhysicalQuantity.Length);
        Product   products = new Product();
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        item.setUnitOfMeasurement(AreaUnitOfMeasurement.MeterSquare);
        assertThrows(IncompatibleUnitsException.class, () -> basketItemService.evaluatePrice(item));
    }

    @Test
    @DisplayName("Eval BasketItem - Negative Amt- exp 45, Qty = 3, UP=15")
    void evaluateProductWithNegative15USDFixPrice() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(-15));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        assertThrows(RuntimeException.class,() -> basketItemService.evaluatePrice(item) );
    }




}