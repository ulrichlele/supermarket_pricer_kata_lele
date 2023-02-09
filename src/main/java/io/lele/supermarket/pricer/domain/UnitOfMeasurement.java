package io.lele.supermarket.pricer.domain;

import java.math.BigDecimal;

public interface UnitOfMeasurement {

    boolean isSIUnit();

    BigDecimal getValue();


}
