package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.enums.UnitOfMeasurement;
import io.lele.supermarket.pricer.service.UnitConverter;

import java.math.BigDecimal;
import java.math.MathContext;

public class DefaultUnitConverterImpl implements UnitConverter {
    @Override
    public <T extends UnitOfMeasurement> BigDecimal convert(BigDecimal initialValue, T initialUnit, T finalUnit) {
        BigDecimal converted;
        if (initialUnit.equals(finalUnit)) {
            converted = initialValue;
        } else if (initialUnit.isSIUnit()) {
            if(initialUnit.order() > finalUnit.order())
                converted = initialValue.divide(finalUnit.getConversion());
            converted = initialValue.multiply(finalUnit.getConversion());
        } else if (finalUnit.isSIUnit()) {
            if(initialUnit.order() > finalUnit.order())
                converted = initialValue.multiply(initialUnit.getConversion());
            converted = initialValue.divide(initialUnit.getConversion());
        } else {
            if(initialUnit.order() > finalUnit.order())
                converted = initialValue.multiply(initialUnit.getConversion()).divide(finalUnit.getConversion());
            converted = initialValue.divide(initialUnit.getConversion()).multiply(finalUnit.getConversion());
        }
        return converted;
    }
}
