package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.service.ProductPricer;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductPricerImpl implements ProductPricer {

    @Override
    public void evaluatePrice(BasketItem item) {
        if(item.getProduct() != null && item.getProduct().getPricingType() != null){
            BigDecimal price = BigDecimal.ZERO;
                    switch (item.getProduct().getPricingType()) {
                case PricePerItem:
                      price =  item.getProduct().getUnitPrice().multiply(item.getQuantity());
                    break;
                case PriceOnQuantity:
                    price = item.getProduct().getUnitPrice().divide(item.getProduct().getPricedQuantity(), MathContext.DECIMAL128).multiply(item.getQuantity());
                    break;
            }
            item.setPrice(price);
        }
    }
    @Override
    public void evaluateBasket(Basket basket) {
        BigDecimal sum = new BigDecimal(0);
        for (BasketItem item :
                basket.getItems()) {
            evaluatePrice(item);
            basket.setTotalPrice(basket.getTotalPrice().add(item.getPrice()));
        }
    }

}
