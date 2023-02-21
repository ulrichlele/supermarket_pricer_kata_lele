package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import io.lele.supermarket.pricer.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ProductMapper implements  BaseJpaModelMapper<Product, ProductJpa>{

    public  Product toDomain(ProductJpa jpa){
        ModelMapper mapper = new ModelMapper();
        TypeMap<ProductJpa, Product> propertyMapper = mapper.createTypeMap(ProductJpa.class, Product.class);
        propertyMapper.addMappings(s -> s.skip(Product::setUnitOfMeasurement));
        Product domain = mapper.map(jpa, Product.class);
        domain.setUnitOfMeasurement(UnitOfMeasurementMapper.toUnitOfMeasurement(jpa.getUnitOfMeasurement()));
        return domain;
    }

    public  ProductJpa toJpaEntity(Product domain){
        ModelMapper mapper = new ModelMapper();
        TypeMap<Product, ProductJpa> propertyMapper = mapper.createTypeMap(Product.class, ProductJpa.class);
        propertyMapper.addMappings(s -> s.skip(ProductJpa::setUnitOfMeasurement));
        ProductJpa jpa = mapper.map(domain, ProductJpa.class);
        if(domain.getUnitOfMeasurement() != null)
            jpa.setUnitOfMeasurement(domain.getUnitOfMeasurement().name());
        return jpa;
    }

}
