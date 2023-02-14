package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.port.in.CreateProductModel;
import io.lele.supermarket.pricer.application.port.in.ProductServicePort;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService implements ProductServicePort {
    private static final String STD_VALIDATION_FAILED  = "Validation.Failed";
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ProductCrudPort productCrudPort;
    public ProductService(ProductCrudPort productCrudPort){
        this.productCrudPort = productCrudPort;
    }
    @Override
    public BaseResponse<Product> create(CreateProductModel model) {
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


}
