package io.lele.supermarket.pricer.adapter.out.jpa.repo;

import io.lele.supermarket.pricer.adapter.out.jpa.model.ProductJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductJpa, String> {
}
