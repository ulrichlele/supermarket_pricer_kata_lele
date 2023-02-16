package io.lele.supermarket.pricer.adapter.out.jpa.adapter;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.BaseJpaModelMapper;
import io.lele.supermarket.pricer.application.port.out.BaseCrudPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public  abstract class BaseJpaAdapter<D, ID, JPA>  implements BaseCrudPort<D, ID> {

    protected BaseJpaModelMapper<D, JPA> mapper;

    protected abstract JpaRepository<JPA, ID> getJpaRepository();

    protected abstract BaseJpaModelMapper getJpaModelMapper();

    @Override
    public D create(D domain) {
        JPA jpaEntity =  (JPA)getJpaModelMapper().toJpaEntity(domain);
        jpaEntity = getJpaRepository().save(jpaEntity);
        return (D) getJpaModelMapper().toDomain(jpaEntity);
    }

    @Override
    public D update(D domain) {
        JPA jpaEntity =  (JPA)getJpaModelMapper().toJpaEntity(domain);
        jpaEntity = getJpaRepository().save(jpaEntity);
        return (D) getJpaModelMapper().toDomain(jpaEntity);
    }

    @Override
    public Optional<D> findById(ID reference) {
        Optional<JPA> product = getJpaRepository().findById(reference);
        if(product.isPresent()){
            return Optional.of((D)getJpaModelMapper().toDomain(product.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean recordExists(ID reference) {
        return getJpaRepository().existsById(reference);
    }
    @Override
    public Set<D> findAll() {
        return getJpaRepository().findAll().stream().map( jpa -> (D)getJpaModelMapper().toDomain(jpa)).collect(Collectors.toSet());
    }
}
