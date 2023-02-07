package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterService;
import io.lele.supermarket.pricer.utils.AmountUtil;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductService  {

    private UnitConverter converter = new DefaultUnitConverterService();


    public void evaluatePrice(BasketItem item) throws IncompatibleUnitsException {
        if (item.getProduct() != null && item.getProduct().getPricingType() != null) {
            BigDecimal price = BigDecimal.ZERO;
            if(item.getProduct().getUnitPrice().compareTo(BigDecimal.ZERO) <0)
                throw new RuntimeException("Negative product unit price");
            switch (item.getProduct().getPricingType()) {
                case PricePerItem:
                    price = item.getProduct().getUnitPrice().multiply(item.getQuantity());
                    break;
                case PriceOnQuantity:
                    price = item.getProduct().getUnitPrice().divide(item.getProduct().getPricedQuantity(), MathContext.DECIMAL128).multiply(item.getQuantity());
                    break;
                case PricePerUnitOfMeasurement:
                    BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                    price =  convertedQty.multiply(item.getProduct().getUnitPrice());
                    break;
            }
            item.setPrice(AmountUtil.scaleAmount(price));
            item.setTotalQuantity(item.getQuantity());
            item.setTotalPrice(item.getPrice());
        }
    }





}
