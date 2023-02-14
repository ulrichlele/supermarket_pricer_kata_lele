package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;

public enum PhysicalQuantity {
    Length, Mass , Area, Volume;

    public UnitOfMeasurement getSIUnit(){
        UnitOfMeasurement unit = null;
        switch (this){
            case Length:
                unit = LengthUnitOfMeasurement.Meter;
                break;
            case Area:
                unit = AreaUnitOfMeasurement.MeterSquare;
                break;
            case Mass:
                unit = MassUnitOfMeasurement.Gram;
                break;
            case Volume:
                unit = VolumeUnitOfMeasurement.Litre;
                break;
        }
        return unit;
    }
}
