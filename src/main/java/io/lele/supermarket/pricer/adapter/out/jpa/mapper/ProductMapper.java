package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import io.lele.supermarket.pricer.core.BaseMapper;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.AreaUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.MassUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import org.hibernate.Length;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ProductMapper {

    public static Product toDomain(ProductJpa jpa){
        ModelMapper mapper = new ModelMapper();
        TypeMap<ProductJpa, Product> propertyMapper = mapper.createTypeMap(ProductJpa.class, Product.class);
        propertyMapper.addMappings(s -> s.skip(Product::setUnitOfMeasurement));
        Product domain = mapper.map(jpa, Product.class);
        domain.setUnitOfMeasurement(UnitOfMeasurementMapper.toUnitOfMeasurement(jpa.getUnitOfMeasurement(), jpa.getPhysicalQuantity()));
        return domain;
    }

    public  static ProductJpa toJpaEntity(Product domain){
        ModelMapper mapper = new ModelMapper();
        TypeMap<Product, ProductJpa> propertyMapper = mapper.createTypeMap(Product.class, ProductJpa.class);
        propertyMapper.addMappings(s -> s.skip(ProductJpa::setUnitOfMeasurement));
        ProductJpa jpa = mapper.map(domain, ProductJpa.class);
        if(domain.getUnitOfMeasurement() != null)
            jpa.setUnitOfMeasurement(domain.getUnitOfMeasurement().name());
        return jpa;
    }

}
