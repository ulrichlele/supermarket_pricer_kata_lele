package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;


public enum LengthUnitOfMeasurement implements UnitOfMeasurement {
    Millimeter(0.001), Centimeter(0.01), Meter(1), Kilometer(1000);

    public final double baseUnitConversion;
    LengthUnitOfMeasurement(double baseUnitConversion){
        this.baseUnitConversion = baseUnitConversion;
    }

    @Override
    public LengthUnitOfMeasurement getBaseUnit() {
        return LengthUnitOfMeasurement.Meter;
    }

    @Override
    public double getBaseUnitConversion() {
        return baseUnitConversion;
    }



}
