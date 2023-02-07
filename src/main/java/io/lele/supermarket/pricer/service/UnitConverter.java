package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConverter {

      <T extends  UnitOfMeasurement> BigDecimal  convert(BigDecimal initialValue, T initialUnit, T finalUnit)throws IncompatibleUnitsException;
}
