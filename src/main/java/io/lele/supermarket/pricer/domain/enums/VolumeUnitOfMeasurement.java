package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public enum VolumeUnitOfMeasurement implements UnitOfMeasurement {
    Millilitre(new BigDecimal(1000)), Litre(new BigDecimal(1)), Kilolitre( new BigDecimal(0.001 ));

    public final  BigDecimal value;

    VolumeUnitOfMeasurement(BigDecimal value){
        this.value = value;
    }

    @Override
    public boolean isSIUnit() {
        return this.equals(Litre) ? true : false;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }
}
