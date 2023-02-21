package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitOfMeasurementMapper {

    private static final Map<String, UnitOfMeasurement> UNITS = new HashMap<>();
    static {

        List<Stream<UnitOfMeasurement>> quantities = Arrays.asList(Arrays.stream(AreaUnitOfMeasurement.values()),
                Arrays.stream(LengthUnitOfMeasurement.values()),
                Arrays.stream(VolumeUnitOfMeasurement.values()),
                Arrays.stream(MassUnitOfMeasurement.values()));
        quantities.forEach( unit -> {
            UNITS.putAll(unit.collect(Collectors.toMap(UnitOfMeasurement::name, item -> item)));
        });
    }

    public static UnitOfMeasurement toUnitOfMeasurement(String strUnit){
        return UNITS.get(strUnit);
    }
}
