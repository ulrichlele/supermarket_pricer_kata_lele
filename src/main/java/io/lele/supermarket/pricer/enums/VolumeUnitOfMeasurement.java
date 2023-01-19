package io.lele.supermarket.pricer.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum VolumeUnitOfMeasurement implements UnitOfMeasurement {
    Millilitre, Litre, Kilolitre;

    @Override
    public boolean isSIUnit() {
        return this.equals(Litre) ? true : false;
    }

    @Override
    public BigDecimal getConversion() {
        return conversions.get(this);
    }

    private static final Map<VolumeUnitOfMeasurement, BigDecimal> conversions;

    static {
        conversions = new HashMap<>();
        conversions.put(Millilitre, new BigDecimal(1000));
        conversions.put(Litre, new BigDecimal(1));
        conversions.put(Kilolitre, new BigDecimal(0.001 ));
    }
}
