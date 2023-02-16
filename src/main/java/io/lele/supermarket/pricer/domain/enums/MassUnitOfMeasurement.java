package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;


public enum MassUnitOfMeasurement implements UnitOfMeasurement {

    Milligram(0.001),  Gram(1), Kilogram(1000);

    public final double baseUnitConversion;
    MassUnitOfMeasurement(double baseUnitConversion){
        this.baseUnitConversion = baseUnitConversion;
    }

    @Override
    public MassUnitOfMeasurement getBaseUnit() {
        return MassUnitOfMeasurement.Gram;
    }

    @Override
    public double getBaseUnitConversion() {
        return baseUnitConversion;
    }
}
