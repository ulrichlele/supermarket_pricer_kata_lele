package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.dto.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.UpdateProductDTO;
import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductRequestPort {

    BaseResponse<Product> create(CreateProductDTO model);

    Optional<Product> findById(String reference);

    BaseResponse<Product> update(UpdateProductDTO model);

    boolean recordExists(String reference);


    BaseResponse<Product> findRecordById(String reference);


    BaseResponse<Set<Product>> findAll();
}
