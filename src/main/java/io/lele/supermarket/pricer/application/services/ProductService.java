package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.ProductRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.product.ProductDtoMapper;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.core.RecordNotFound;
import io.lele.supermarket.pricer.core.ValidationException;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductService implements ProductRequestPort {
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ProductCrudPort productCrudPort;
    public ProductService(ProductCrudPort productCrudPort){
        this.productCrudPort = productCrudPort;
    }
    @Override
    public Product create(CreateProductDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        if(!validations.isEmpty())
            throw new ValidationException(Contants.STD_VALIDATION_FAILED, validations);
        Product domain = ProductDtoMapper.INSTANCE.toCreateDomain(dto);
        domain = productCrudPort.update(domain);
        return domain;
    }

    @Override
    public Product update(UpdateProductDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        if(!validations.isEmpty() && !this.recordExists(dto.getReference())){
            throw new RecordNotFound("Product: "+dto.getReference());
        }
        if(!validations.isEmpty())
            throw new ValidationException(Contants.STD_VALIDATION_FAILED, validations);
            //Product domain = this.findById(dto.getReference()).get();
            Product domain = ProductDtoMapper.INSTANCE.toCreateDomain(dto);
            domain = productCrudPort.update(domain);
        return domain;
    }

    @Override
    public boolean recordExists(String reference) {
        return productCrudPort.recordExists(reference);
    }

    @Override
    public Optional<Product> findById(String reference) {
        return productCrudPort.findById(reference);
    }

    @Override
    public Product findRecordById(String reference) {
        return productCrudPort.findById(reference).orElseThrow(() -> new RecordNotFound(Contants.STD_RECORD_NOT_FOUND));
    }

    @Override
    public Set<Product> findAll() {
        return this.productCrudPort.findAll();
    }


}
