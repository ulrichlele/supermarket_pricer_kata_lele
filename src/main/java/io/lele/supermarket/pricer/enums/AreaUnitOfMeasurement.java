package io.lele.supermarket.pricer.enums;

import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
