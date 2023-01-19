package io.lele.supermarket.pricer.enums;

import java.math.BigDecimal;

public interface UnitOfMeasurement {

    boolean isSIUnit();

    BigDecimal getConversion();

    int order();
}
