package io.lele.supermarket.pricer.enums;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum LengthUnitOfMeasurement implements UnitOfMeasurement{
    Millimeter, Centimeter, Meter, Kilometer;

    @Override
    public boolean isSIUnit() {
        return this.equals(Meter) ? true : false;
    }

    @Override
    public BigDecimal getConversion() {
        return conversions.get(this);
    }

    @Override
    public int order() {
        return this.ordinal();
    }

    private static final Map<LengthUnitOfMeasurement, BigDecimal> conversions;

    static {
        conversions = new HashMap<>();
        conversions.put(Millimeter, new BigDecimal(1000));
        conversions.put(Centimeter, new BigDecimal(100));
        conversions.put(Meter, new BigDecimal(1));
        conversions.put(Kilometer, new BigDecimal(0.001 ));
    }
}
