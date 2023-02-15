package io.lele.supermarket.pricer.adapter.out.jpa.adapter;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.ProductMapper;
import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import io.lele.supermarket.pricer.adapter.out.jpa.repo.ProductRepository;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductJpaAdapter implements ProductCrudPort {

    private ProductRepository repository;
    public ProductJpaAdapter(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public Product create(Product domain) {
        ProductJpa  jpa = repository.save(ProductMapper.toJpaEntity(domain));
        return  ProductMapper.toDomain(jpa);
    }

    @Override
    public Product update(Product domain) {
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

    @Override
    public boolean recordExists(String reference) {
        return repository.existsById(reference);
    }

    @Override
    public Set<Product> findAll() {
        return repository.findAll().stream().map( jpa -> ProductMapper.toDomain(jpa)).collect(Collectors.toSet());
    }
}
