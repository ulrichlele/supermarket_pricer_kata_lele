package io.lele.supermarket.pricer.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum AreaUnitOfMeasurement implements UnitOfMeasurement {
    MillimeterSquare, CentimeterSquare, MeterSquare, KilometerSquare;

    @Override
    public boolean isSIUnit() {
        return this.equals(MeterSquare) ? true : false;
    }

    @Override
    public BigDecimal getConversion() {
        return conversions.get(this);
    }

    private static final Map<AreaUnitOfMeasurement, BigDecimal> conversions;

    static {
        conversions = new HashMap<>();
        conversions.put(MillimeterSquare, new BigDecimal(1000));
        conversions.put(CentimeterSquare, new BigDecimal(100));
        conversions.put(MeterSquare, new BigDecimal(1));
        conversions.put(KilometerSquare, new BigDecimal(0.001 ));
    }
}
