package io.lele.supermarket.pricer.domain;

import java.math.BigDecimal;

public interface UnitOfMeasurement {

    UnitOfMeasurement getBaseUnit();

    double getBaseUnitConversion();

    String name();


}
