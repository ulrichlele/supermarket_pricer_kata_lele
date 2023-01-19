package io.lele.supermarket.pricer.model;

import java.math.BigDecimal;

public interface UnitOfMeasurement {

    boolean isSIUnit();

    BigDecimal getConversion();

}
