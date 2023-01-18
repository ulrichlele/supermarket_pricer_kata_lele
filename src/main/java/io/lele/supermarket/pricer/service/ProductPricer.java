package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;

import java.math.BigDecimal;

public interface ProductPricer {
    void evaluatePrice(BasketItem item);

    void evaluateBasket(Basket basket);

}
