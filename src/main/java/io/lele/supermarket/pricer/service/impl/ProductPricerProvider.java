package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.service.ProductPricer;
import io.lele.supermarket.pricer.service.UnitConverter;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductPricerProvider implements ProductPricer {

    private UnitConverter converter = new DefaultUnitConverterProvider();

    private static final MathContext PRICE_ROUNDING  = new MathContext(2);

    @Override
    public void evaluatePrice(BasketItem item) throws Exception{
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
                    }else{
                        throw new IncompatibleUnitsException("Incompatible Unit of measurement");
                    }
                    break;
            }
            price = price.round(PRICE_ROUNDING);
            item.setPrice(price);
        }
    }

    @Override
    public void evaluateBasket(Basket basket) throws Exception {
        BigDecimal sum = new BigDecimal(0);
        for (BasketItem item :
                basket.getItems()) {
            evaluatePrice(item);
            basket.setTotalPrice(basket.getTotalPrice().add(item.getPrice()));
        }
    }

}