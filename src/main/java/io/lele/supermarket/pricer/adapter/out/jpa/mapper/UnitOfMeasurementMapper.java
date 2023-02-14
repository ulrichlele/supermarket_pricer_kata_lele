package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.AreaUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.MassUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;

public class UnitOfMeasurementMapper {

    public static UnitOfMeasurement toUnitOfMeasurement(String strUnit, PhysicalQuantity quantity){
        UnitOfMeasurement unitOfMeasurement = null;
        if(strUnit != null){
            if(quantity == null){
                throw new RuntimeException("PhysicalQuantity.null");
            }
            switch (quantity){
                case Area:
                    unitOfMeasurement = AreaUnitOfMeasurement.valueOf(strUnit);
                    break;
                case Length:
                    unitOfMeasurement = LengthUnitOfMeasurement.valueOf(strUnit);
                    break;
                case Mass:
                    unitOfMeasurement = MassUnitOfMeasurement.valueOf(strUnit);
                    break;
            }
        }
        return unitOfMeasurement;
    }
}
