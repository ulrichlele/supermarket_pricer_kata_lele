package io.lele.supermarket.pricer.application.port.out;

import io.lele.supermarket.pricer.domain.Product;

import java.util.Optional;

public interface ProductCrudPort {

    Product create(Product domain);

    Optional<Product> findById(String reference);


}
