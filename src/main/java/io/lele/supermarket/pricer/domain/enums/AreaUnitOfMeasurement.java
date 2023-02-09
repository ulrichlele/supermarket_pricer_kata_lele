package io.lele.supermarket.pricer.domain.enums;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public enum AreaUnitOfMeasurement implements UnitOfMeasurement {
    MillimeterSquare(new BigDecimal(1000)), CentimeterSquare(new BigDecimal(100)), MeterSquare(new BigDecimal(1)), KilometerSquare(new BigDecimal(0.001));

    public final BigDecimal value;
     AreaUnitOfMeasurement(BigDecimal value){
        this.value = value;
    }

    @Override
    public boolean isSIUnit() {
        return this.equals(MeterSquare) ? true : false;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }


}
