package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.enums.UnitOfMeasurement;
import io.lele.supermarket.pricer.service.UnitConverter;

import java.math.BigDecimal;

public class DefaultUnitConverterImpl  implements UnitConverter {
    @Override
    public <T extends UnitOfMeasurement> BigDecimal convert(BigDecimal initialValue, T initialUnit, T finalUnit) {
        return null;
    }
}
