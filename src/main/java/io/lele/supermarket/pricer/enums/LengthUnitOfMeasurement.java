package io.lele.supermarket.pricer.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum LengthUnitOfMeasurement implements UnitOfMeasurement {
    Millimeter(new BigDecimal(1000)), Centimeter(new BigDecimal(100)), Meter(new BigDecimal(1)), Kilometer(new BigDecimal(0.001 ));

    public final BigDecimal value;

    LengthUnitOfMeasurement(BigDecimal value){
        this.value = value;
    }
    @Override
    public boolean isSIUnit() {
        return this.equals(Meter) ? true : false;
    }
    @Override
    public BigDecimal getValue() {
        return value;
    }
}
