package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;


public enum VolumeUnitOfMeasurement implements UnitOfMeasurement {
    Millilitre(0.001), Litre(1), Kilolitre(1000);

    public final double baseUnitConversion;

    VolumeUnitOfMeasurement(double baseUnitConversion){
        this.baseUnitConversion = baseUnitConversion;
    }

    @Override
    public VolumeUnitOfMeasurement getBaseUnit() {
        return VolumeUnitOfMeasurement.Litre;
    }

    @Override
    public double getBaseUnitConversion() {
        return baseUnitConversion;
    }
}
