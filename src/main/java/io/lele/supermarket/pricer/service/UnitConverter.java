package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.enums.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConverter {

      <T extends  UnitOfMeasurement> BigDecimal  convert(BigDecimal initialValue, T initialUnit, T finalUnit);
}
