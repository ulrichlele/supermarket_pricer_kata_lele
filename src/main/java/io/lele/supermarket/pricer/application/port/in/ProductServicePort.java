package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.base.BaseResponse;
import io.lele.supermarket.pricer.domain.Product;
import jakarta.validation.Valid;

public interface ProductServicePort {

    BaseResponse<Product> create(@Valid CreateProductModel model);

}
