package io.lele.supermarket.pricer.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum MassUnitOfMeasurement implements UnitOfMeasurement {
    Milligram, Gram, Kilogram;

    @Override
    public boolean isSIUnit() {
        return this.equals(Gram) ? true : false;
    }

    @Override
    public BigDecimal getConversion() {
        return conversions.get(this);
    }

    private static final Map<MassUnitOfMeasurement, BigDecimal> conversions;

    static {
        conversions = new HashMap<>();
        conversions.put(Milligram, new BigDecimal(1000));
        conversions.put(Gram, new BigDecimal(1));
        conversions.put(Kilogram, new BigDecimal(0.001 ));
    }
}
