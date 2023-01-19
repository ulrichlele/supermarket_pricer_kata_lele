package io.lele.supermarket.pricer.enums;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum LengthUnitOfMeasurement implements UnitOfMeasurement{
    Millimeter, Centimeter, Meter, Kilometer;


    @Override
    public boolean isSIUnit() {
        return false;
    }

    @Override
    public BigDecimal getConversion() {
        return null;
    }
}
