package io.lele.supermarket.pricer.model.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum MassUnitOfMeasurement implements UnitOfMeasurement {
    Milligram(new BigDecimal(1000)), Gram(new BigDecimal(1)), Kilogram( new BigDecimal(0.001 ));

    public final BigDecimal value;

    MassUnitOfMeasurement(BigDecimal value){
        this.value = value;
    }

    @Override
    public boolean isSIUnit() {
        return this.equals(Gram) ? true : false;
    }
    @Override
    public BigDecimal getValue() {
        return value;
    }
}
