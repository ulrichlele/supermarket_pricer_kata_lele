package io.lele.supermarket.pricer.adapter.out.jpa.mapper;


public interface BaseJpaModelMapper<D, JPA> {

    D toDomain(JPA jpa);

    JPA toJpaEntity(D domain);
}
