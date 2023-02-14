package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.adapter.out.jpa.model.PromotionJpa;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.AreaUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.LengthUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.MassUnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class PromotionMapper {

    public static Promotion toDomain(PromotionJpa jpa){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(jpa, Promotion.class);
    }

    public  static PromotionJpa toJpaEntity(Promotion domain){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(domain, PromotionJpa.class);
    }

}
