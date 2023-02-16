package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.port.in.dto.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.ProductRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.ProductDtoMapper;
import io.lele.supermarket.pricer.application.port.in.dto.UpdateProductDTO;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductService implements ProductRequestPort {
    private static final String STD_VALIDATION_FAILED  = "Validation.Failed";
    private static final String STD_RECORD_NOT_FOUND  = "Record.Not.Found";
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ProductCrudPort productCrudPort;
    public ProductService(ProductCrudPort productCrudPort){
        this.productCrudPort = productCrudPort;
    }
    @Override
    public BaseResponse<Product> create(CreateProductDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        BaseResponse<Product> response = new BaseResponse<>();
        if(!validations.isEmpty()){
            logger.error(validations.toString());
            response.setResults(validations);
            response.setMessage(STD_VALIDATION_FAILED);
        }else{
            Product product = ProductDtoMapper.toCreateDomain(dto, new Product());
            product.setUnitPrice(AmountUtil.scaleAmount(product.getUnitPrice()));
            response.setSuccess(true);
            response.setData(product);
            productCrudPort.create(product);
        }

        return response;
    }

    @Override
    public BaseResponse<Product> update(UpdateProductDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        BaseResponse<Product> response = new BaseResponse<>();
        if(!validations.isEmpty()){
            logger.error(validations.toString());
            response.setResults(validations);
            response.setMessage(STD_VALIDATION_FAILED);
        }else{
            Optional<Product> existing = this.findById(dto.getReference());
            if(existing.isPresent()){
                Product product = existing.get();
                product = ProductDtoMapper.toDomain(dto, product);
                product.setUnitPrice(AmountUtil.scaleAmount(product.getUnitPrice()));
                productCrudPort.update(product);
                response.setSuccess(true);
                response.setData(product);
            }else{
                validations.add(new ValidatorResult("Record.Not.Found", Product.class, "reference"));
                logger.error(validations.toString());
                response.setResults(validations);
                response.setMessage(STD_VALIDATION_FAILED);
            }
        }
        return response;
    }

    @Override
    public boolean recordExists(String reference) {
        return productCrudPort.recordExists(reference);
    }


    private BaseResponse<Product> persiste(UpdateProductDTO model) {
        Set<ValidatorResult>  validations = model.validate();
        BaseResponse<Product> response = new BaseResponse<>();
        if(!validations.isEmpty()){
            logger.error(validations.toString());
            response.setResults(validations);
            response.setMessage(STD_VALIDATION_FAILED);
        }else{
            UnitOfMeasurement unitOfMeasurement = UnitOfMeasurementMapper.toUnitOfMeasurement(model.getUnitOfMeasurement(), model.getPhysicalQuantity());
            Product product = new Product(model.getName(), model.getUnitPrice(), model.getPricedQuantity(), model.getPricingType(),unitOfMeasurement, model.getPhysicalQuantity());
            response.setSuccess(true);
            response.setData(product);
            productCrudPort.create(product);
        }
        return response;
    }

    @Override
    public Optional<Product> findById(String reference) {
        return productCrudPort.findById(reference);
    }


    @Override
    public BaseResponse<Product> findRecordById(String reference) {
        BaseResponse<Product> response = new BaseResponse<>();
        if(this.productCrudPort.recordExists(reference)){
            response.setSuccess(true);
            response.setData(productCrudPort.findById(reference).get());
        }else{
              response.setMessage(STD_RECORD_NOT_FOUND);
        }
        return response;
    }

    @Override
    public BaseResponse<Set<Product>> findAll() {
        BaseResponse<Set<Product>> response = new BaseResponse<>();
        response.setData(this.productCrudPort.findAll());
        response.setSuccess(true);
        return response;
    }


}
