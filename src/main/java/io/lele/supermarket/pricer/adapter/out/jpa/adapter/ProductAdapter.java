package io.lele.supermarket.pricer.adapter.out.jpa.adapter;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.ProductMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import io.lele.supermarket.pricer.adapter.out.jpa.repo.ProductRepository;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductAdapter implements ProductCrudPort {

    private ProductRepository repository;
    public ProductAdapter(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public Product create(Product domain) {
        ProductJpa  jpa = repository.save(ProductMapper.toJpaEntity(domain));
        return  ProductMapper.toDomain(jpa);
    }

    @Override
    public Optional<Product> findById(String reference) {
        Optional<ProductJpa> product = repository.findById(reference);
        if(product.isPresent()){
            return Optional.of(ProductMapper.toDomain(product.get()));
        }else{
            return Optional.empty();
        }
    }
}
