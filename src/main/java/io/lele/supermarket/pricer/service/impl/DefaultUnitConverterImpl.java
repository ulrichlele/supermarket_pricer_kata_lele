package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;
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
                converted = initialValue.multiply(finalUnit.getConversion(), MathContext.DECIMAL64);
        } else if (finalUnit.isSIUnit()) {
            converted = initialValue.divide(initialUnit.getConversion(), MathContext.DECIMAL64);
        } else {
                BigDecimal convertedToSIUnit = initialValue.divide(initialUnit.getConversion(), MathContext.DECIMAL64);
                converted = convertedToSIUnit.multiply(finalUnit.getConversion(), MathContext.DECIMAL64);
        }
        return converted;
    }
}
