package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConverter {
      <T extends  UnitOfMeasurement> BigDecimal  convert(BigDecimal initialValue, T initialUnit, T finalUnit)throws IncompatibleUnitsException;
}
