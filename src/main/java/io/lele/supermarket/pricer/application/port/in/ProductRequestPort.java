package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductRequestPort {

    Product create(CreateProductDTO model);

    Optional<Product> findById(String reference);

    Product update(UpdateProductDTO model);

    boolean recordExists(String reference);

    Product findRecordById(String reference);


    Set<Product> findAll();
}
