package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.model.UnitOfMeasurement;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    private static UnitConverter converter;

    @BeforeAll
    @DisplayName("Initialize unit converter")
    static void init(){
        converter = new DefaultUnitConverterImpl();
    }

    @Test
    @DisplayName("Should return true if Meter is Si UNit of length")
    void checkMeterIsSiUnitOfLength(){
        UnitOfMeasurement unit = LengthUnitOfMeasurement.Meter;
        assertTrue(unit.isSIUnit());
    }

    @Test
    @DisplayName("Should return a conversion of 1 as Meter is Si unit of length")
    void OneIsConversionOfMeterAsSIUnitOfLength(){
        UnitOfMeasurement unit = LengthUnitOfMeasurement.Meter;
        assertEquals(BigDecimal.ONE, unit.getConversion());
    }

    @Test
    @DisplayName("Conv 10cm to m exp 0.1m")
    void concert10CentimeterToMetter(){
        BigDecimal initialValue = new BigDecimal(10);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Meter;
        assertEquals(new BigDecimal(0.1, new MathContext(1)), converter.convert(initialValue,initialUnit, finalUnit));
    }

    @Test
    @DisplayName("Conv 15m to mm exp 15000mm")
    void concert15MerterToMillimeter(){
        BigDecimal initialValue = new BigDecimal(15);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Meter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Millimeter;
        assertEquals(new BigDecimal(15000), converter.convert(initialValue,initialUnit, finalUnit));
    }

    @Test
    @DisplayName("Conv 4000cm to km exp 0,04km")
    void concert4000CentimeterToKilometter(){
        BigDecimal initialValue = new BigDecimal(4000);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Kilometer;
        assertEquals(new BigDecimal(0.04, new MathContext(1)), converter.convert(initialValue,initialUnit, finalUnit).round(new MathContext(1)));
    }

    @Test
    @DisplayName("Conv 10cm to cm exp 10cm")
    void concert10CentimeterToCentimeter(){
        BigDecimal initialValue = new BigDecimal(10);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Centimeter;
        assertEquals(new BigDecimal(10.0), converter.convert(initialValue,initialUnit, finalUnit));

    }
}