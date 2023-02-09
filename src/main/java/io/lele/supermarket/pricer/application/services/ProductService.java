package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.port.in.CreateProductModel;
import io.lele.supermarket.pricer.application.port.in.ProductServicePort;
import io.lele.supermarket.pricer.application.port.in.base.BaseResponse;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

@Service
public class ProductService implements ProductServicePort {


    @Override
    public BaseResponse<Product> create(CreateProductModel model) {
        return null;
    }
}
