package io.lele.supermarket.pricer.application.port.in.dto.product;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.math.BigDecimal;

@Mapper
public interface ProductDtoMapper {

    ProductDtoMapper INSTANCE = Mappers.getMapper(ProductDtoMapper.class);

    @Mapping(source = "unitPrice", target = "unitPrice", qualifiedByName = "amountMapper" )
    @Mapping(source = "unitOfMeasurement", target = "unitOfMeasurement", qualifiedByName = "productDtoUnitOfMeasurementMapper")
    Product toUpdateDomain(UpdateProductDTO dto);

    @Mapping(source = "unitPrice", target = "unitPrice", qualifiedByName = "amountMapper" )
    @Mapping(source = "unitOfMeasurement", target = "unitOfMeasurement", qualifiedByName = "productDtoUnitOfMeasurementMapper")
    Product toCreateDomain(CreateProductDTO dto);

    @Named("productDtoUnitOfMeasurementMapper")
    static UnitOfMeasurement unitOfMeasurementMapper(String stringUnitOfMeasurement) {
        return UnitOfMeasurementMapper.toUnitOfMeasurement(stringUnitOfMeasurement);
    }

    @Named("amountMapper")
    static BigDecimal unitOfMeasurementMapper(BigDecimal unitPrice) {
        return AmountUtil.scaleAmount(unitPrice);
    }

}
