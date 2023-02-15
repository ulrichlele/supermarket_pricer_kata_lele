package io.lele.supermarket.pricer.application.port.in.dto;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ProductDtoMapper {

    public static Product toDomain(UpdateProductDTO dto, Product product){
        Product domain = product != null ? product : new Product();
        return toCreateDomain(dto, product);
    }

    public static Product toCreateDomain(CreateProductDTO dto, Product product){
        Product domain = product != null ? product : new Product();
        ModelMapper mapper = new ModelMapper();
        TypeMap<UpdateProductDTO, Product> propertyMapper = mapper.createTypeMap(UpdateProductDTO.class, Product.class);
        propertyMapper.addMappings(s -> s.skip(Product::setUnitOfMeasurement));
        mapper.map(dto, domain);
        domain.setUnitOfMeasurement(UnitOfMeasurementMapper.toUnitOfMeasurement(dto.getUnitOfMeasurement(), dto.getPhysicalQuantity()));
        return domain;
    }
}
