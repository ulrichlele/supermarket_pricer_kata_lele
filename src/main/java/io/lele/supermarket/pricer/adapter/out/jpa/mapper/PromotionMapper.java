package io.lele.supermarket.pricer.adapter.out.jpa.mapper;

import io.lele.supermarket.pricer.adapter.out.jpa.model.PromotionJpa;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.Promotion;
import org.modelmapper.ModelMapper;

public class PromotionMapper implements BaseJpaModelMapper<Promotion, PromotionJpa>{

    public  Promotion toDomain(PromotionJpa jpa){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(jpa, Promotion.class);
    }

    public   PromotionJpa toJpaEntity(Promotion domain){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(domain, PromotionJpa.class);
    }

}
