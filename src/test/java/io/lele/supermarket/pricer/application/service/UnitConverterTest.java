package io.lele.supermarket.pricer.application.service;

import io.lele.supermarket.pricer.application.services.UnitConverter;
import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.domain.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnitConverterTest {

    @Autowired
    private UnitConverter converter;


    @Test
    @DisplayName("Should return true if Meter is base UNit of length")
    void checkMeterIsBaseUnitOfLength(){
        UnitOfMeasurement unit = LengthUnitOfMeasurement.Meter;
        assertTrue(unit.equals(unit.getBaseUnit()));
    }

    @Test
    @DisplayName("Should return a conversion of 1 as Meter is base unit of length")
    void OneIsConversionOfMeterAsBaseUnitOfLength()  {
        UnitOfMeasurement unit = LengthUnitOfMeasurement.Meter;
        assertEquals(BigDecimal.ONE.doubleValue(), unit.getBaseUnitConversion());
    }

    @Test
    @DisplayName("Conv 10cm to m exp 0.1m")
    void concert10CentimeterToMetter()throws IncompatibleUnitsException {
        BigDecimal initialValue = new BigDecimal(10);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Meter;
        assertEquals(new BigDecimal(0.1, new MathContext(1)).doubleValue(), converter.convert(initialValue,initialUnit, finalUnit).doubleValue());
    }

    @Test
    @DisplayName("Conv 15m to mm exp 15000mm")
    void concert15MerterToMillimeter()throws IncompatibleUnitsException{
        BigDecimal initialValue = new BigDecimal(15);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Meter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Millimeter;
        assertEquals(new BigDecimal(15000).doubleValue(), converter.convert(initialValue,initialUnit, finalUnit).doubleValue());
    }

    @Test
    @DisplayName("Conv 4000cm to km exp 0,04km")
    void concert4000CentimeterToKilometter() throws IncompatibleUnitsException {
        BigDecimal initialValue = new BigDecimal(4000);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Kilometer;
        assertEquals(new BigDecimal(0.04, new MathContext(1)), converter.convert(initialValue,initialUnit, finalUnit).round(new MathContext(1)));
    }

    @Test
    @DisplayName("Conv 10cm to cm exp 10cm")
    void concert10CentimeterToCentimeter() throws IncompatibleUnitsException {
        BigDecimal initialValue = new BigDecimal(10);
        UnitOfMeasurement  initialUnit = LengthUnitOfMeasurement.Centimeter;
        UnitOfMeasurement finalUnit = LengthUnitOfMeasurement.Centimeter;
        assertEquals(new BigDecimal(10.0), converter.convert(initialValue,initialUnit, finalUnit));
    }
}