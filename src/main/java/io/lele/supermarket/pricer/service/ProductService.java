package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterService;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductService  {

    private UnitConverter converter = new DefaultUnitConverterService();

    private static final MathContext PRICE_ROUNDING  = new MathContext(2);


    public void evaluatePrice(BasketItem item) throws IncompatibleUnitsException{
        if (item.getProduct() != null && item.getProduct().getPricingType() != null) {
            BigDecimal price = BigDecimal.ZERO;
            switch (item.getProduct().getPricingType()) {
                case PricePerItem:
                    price = item.getProduct().getUnitPrice().multiply(item.getQuantity());
                    break;
                case PriceOnQuantity:
                    price = item.getProduct().getUnitPrice().divide(item.getProduct().getPricedQuantity(), MathContext.DECIMAL128).multiply(item.getQuantity());
                    break;
                case PricePerUnitOfMeasurement:
                    if(item.getProduct().getUnitOfMeasurement().getClass().getCanonicalName().equals(item.getUnitOfMeasurement().getClass().getCanonicalName())){
                        if(item.getProduct().getUnitOfMeasurement().equals(item.getUnitOfMeasurement())){
                            price = item.getProduct().getUnitPrice().multiply(item.getQuantity());
                        }else{
                            BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                            price =  convertedQty.multiply(item.getProduct().getUnitPrice());
                        }
                    }else{                        String basketItemUnit = item.getProduct().getUnitOfMeasurement().getClass().getName();
                        String productUnit = item.getUnitOfMeasurement().getClass().getName();
                        String message = String.format("BasketItem unit : %s; Product unit: %s",basketItemUnit,  productUnit);
                        throw new IncompatibleUnitsException(message);
                    }
                    break;
            }
            price = price.round(PRICE_ROUNDING);
            item.setPrice(price);
        }
    }

}
