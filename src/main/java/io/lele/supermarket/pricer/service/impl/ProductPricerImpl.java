package io.lele.supermarket.pricer.service.impl;

import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.service.ProductPricer;

import java.math.BigDecimal;

public class ProductPricerImpl implements ProductPricer {

    @Override
    public void evaluatePrice(BasketItem item) {
        BigDecimal price =  item.getProduct().getUnitPrice().multiply(item.getQuantity());
        item.setPrice(price);
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
