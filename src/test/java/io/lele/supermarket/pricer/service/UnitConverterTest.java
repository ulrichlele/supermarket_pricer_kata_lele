package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.enums.UnitOfMeasurement;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterImpl;
import io.lele.supermarket.pricer.service.impl.ProductPricerImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    private static UnitConverter converter;

    @BeforeAll
    @DisplayName("Initialize unit converter")
    static void init(){
        converter = new DefaultUnitConverterImpl();
    }

    @Test
    @DisplayName("Convert - Flat Amt- exp 45, Qty = 3, UP=15")
    void concert(){
    }


}