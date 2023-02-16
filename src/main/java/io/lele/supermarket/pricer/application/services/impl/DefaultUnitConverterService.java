package io.lele.supermarket.pricer.application.services.impl;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.application.services.UnitConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
@Service
public class DefaultUnitConverterService implements UnitConverter {

    public static final MathContext DEFAULT_PRECISION = MathContext.DECIMAL64;

    @Override
    public <T extends UnitOfMeasurement> BigDecimal convert(BigDecimal initialValue, T initialUnit, T finalUnit) throws IncompatibleUnitsException{

        if(convertible(initialUnit, finalUnit)){
            throwIncompatibleUnitsException(initialUnit, finalUnit);
        }
        BigDecimal converted;
        if (initialUnit.equals(finalUnit)) {
            converted = initialValue;
        } else if (initialUnit.equals(initialUnit.getBaseUnit())) {
                converted = initialValue.divide(BigDecimal.valueOf(finalUnit.getBaseUnitConversion()), DEFAULT_PRECISION);
        } else if (finalUnit.equals(finalUnit.getBaseUnit())) {
            converted = initialValue.multiply(BigDecimal.valueOf(initialUnit.getBaseUnitConversion()), DEFAULT_PRECISION);
        } else {
                BigDecimal convertedToSIUnit = initialValue.multiply(BigDecimal.valueOf(initialUnit.getBaseUnitConversion()), DEFAULT_PRECISION);
                converted = convertedToSIUnit.divide(BigDecimal.valueOf(finalUnit.getBaseUnitConversion()), DEFAULT_PRECISION);
        }
        return converted;
    }

    public <T extends  UnitOfMeasurement> boolean convertible( T initialUnit, T finalUnit){
        return  !initialUnit.getClass().getCanonicalName().equals(finalUnit.getClass().getCanonicalName());
    }

    private <T extends  UnitOfMeasurement> void throwIncompatibleUnitsException( T initialUnit, T finalUnit) throws IncompatibleUnitsException{
        String message = String.format("Initial unit : %s; Final unit: %s",initialUnit.getClass().getName(),  finalUnit.getClass().getName());
        throw new IncompatibleUnitsException(message);
    }


}
