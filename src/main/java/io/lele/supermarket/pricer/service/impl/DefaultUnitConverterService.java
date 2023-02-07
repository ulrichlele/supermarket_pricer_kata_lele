package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.UnitOfMeasurement;
import io.lele.supermarket.pricer.service.UnitConverter;

import java.math.BigDecimal;
import java.math.MathContext;

public class DefaultUnitConverterService implements UnitConverter {

    public static final MathContext DEFAULT_PRECISION = MathContext.DECIMAL64;

    @Override
    public <T extends UnitOfMeasurement> BigDecimal convert(BigDecimal initialValue, T initialUnit, T finalUnit) throws IncompatibleUnitsException{

        if(!initialUnit.getClass().getCanonicalName().equals(finalUnit.getClass().getCanonicalName())){
            String message = String.format("BasketItem unit : %s; Product unit: %s",initialUnit.getClass().getName(),  finalUnit.getClass().getName());
            throw new IncompatibleUnitsException(message);
        }
        BigDecimal converted;
        if (initialUnit.equals(finalUnit)) {
            converted = initialValue;
        } else if (initialUnit.isSIUnit()) {
                converted = initialValue.multiply(finalUnit.getValue(), DEFAULT_PRECISION);
        } else if (finalUnit.isSIUnit()) {
            converted = initialValue.divide(initialUnit.getValue(), DEFAULT_PRECISION);
        } else {
                BigDecimal convertedToSIUnit = initialValue.divide(initialUnit.getValue(), DEFAULT_PRECISION);
                converted = convertedToSIUnit.multiply(finalUnit.getValue(), DEFAULT_PRECISION);
        }
        return converted;
    }
}
