package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;


public enum AreaUnitOfMeasurement implements UnitOfMeasurement {
    MillimeterSquare(0.001), CentimeterSquare(0.01), MeterSquare(1), KilometerSquare(1000);

    public final double baseUnitConversion;
     AreaUnitOfMeasurement(double baseUnitConversion){
        this.baseUnitConversion = baseUnitConversion;
    }

    @Override
    public AreaUnitOfMeasurement getBaseUnit() {
        return AreaUnitOfMeasurement.MeterSquare;
    }

    @Override
    public double getBaseUnitConversion() {
        return baseUnitConversion;
    }


}
