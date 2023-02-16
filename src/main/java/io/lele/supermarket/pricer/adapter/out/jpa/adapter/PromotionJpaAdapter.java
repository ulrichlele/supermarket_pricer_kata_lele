package io.lele.supermarket.pricer.adapter.out.jpa.adapter;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.BaseJpaModelMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.mapper.PromotionMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.model.PromotionJpa;
import io.lele.supermarket.pricer.adapter.out.jpa.repo.PromotionRepository;
import io.lele.supermarket.pricer.application.port.out.PromotionCrudPort;
import io.lele.supermarket.pricer.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class PromotionJpaAdapter extends BaseJpaAdapter<Promotion, String, PromotionJpa> implements PromotionCrudPort {

    private PromotionRepository repository;
    
    private BaseJpaModelMapper mapper = new PromotionMapper();
    
    public PromotionJpaAdapter(PromotionRepository repository){
        this.repository = repository;
    }

    @Override  
    protected JpaRepository getJpaRepository() {
        return this.repository;
    }

    @Override
    protected BaseJpaModelMapper getJpaModelMapper() {
        return this.mapper;
    }
 
}
