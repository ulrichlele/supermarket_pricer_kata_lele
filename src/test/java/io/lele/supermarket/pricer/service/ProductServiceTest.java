package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.model.enums.AreaUnitOfMeasurement;
import io.lele.supermarket.pricer.model.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.model.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.model.enums.PricingType;
import io.lele.supermarket.pricer.utils.AmountUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private static ProductService productService;


    @BeforeAll
    @DisplayName("Initialize products and pricer")
    static void init(){
        productService = new ProductService();
    }

    @Test
    @DisplayName("Eval BasketItem - Flat Amt- exp 45, Qty = 3, UP=15")
    void evaluateProductWithFixPrice45USD() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        productService.evaluatePrice(item);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), item.getPrice());
    }

    @Test
    @DisplayName("Eval BasketItem - Throws EX - Length to Area")
    void evaluatePriceGeneratesIncompatibleUnitsException() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15), new BigDecimal(1), PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        Product   products = new Product();
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        item.setUnitOfMeasurement(AreaUnitOfMeasurement.MeterSquare);
        item.setPhysicalQuantity(PhysicalQuantity.Area);
        assertThrows(IncompatibleUnitsException.class, () -> productService.evaluatePrice(item));
    }





}