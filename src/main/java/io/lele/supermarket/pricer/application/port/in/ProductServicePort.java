package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.domain.Product;

import java.util.Optional;

public interface ProductServicePort {

    BaseResponse<Product> create(CreateProductModel model);

    Optional<Product> findById(String reference);

}
