package io.lele.supermarket.pricer.adapter.out.jpa.adapter;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.BaseJpaModelMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.mapper.ProductMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import io.lele.supermarket.pricer.adapter.out.jpa.repo.ProductRepository;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductJpaAdapter extends BaseJpaAdapter<Product, String, ProductJpa> implements ProductCrudPort {

    private ProductRepository repository;

    private BaseJpaModelMapper mapper = new ProductMapper();

    public ProductJpaAdapter(ProductRepository repository){
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
