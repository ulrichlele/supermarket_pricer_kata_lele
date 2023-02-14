package io.lele.supermarket.pricer.core;

import org.modelmapper.ModelMapper;

public class BaseMapper {
    private static final ModelMapper mapper = new ModelMapper();


    public static <D, E> E map(D source, E destination){
        mapper.map(source, destination);
        return destination;
    }
}
