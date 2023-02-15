package io.lele.supermarket.pricer.application.port.out;

import io.lele.supermarket.pricer.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductCrudPort {

    Product create(Product domain);

    Product update(Product domain);

    Optional<Product> findById(String reference);

    public boolean recordExists(String reference);

    Set<Product> findAll();
}
