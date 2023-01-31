package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;

import java.math.BigDecimal;
import java.math.MathContext;

public class BasketService {

    private ProductService productService = new ProductService();

    private static final MathContext PRICE_ROUNDING  = new MathContext(2);


   // @Override
    public void evaluatePrice(Basket basket) throws Exception {
        BigDecimal sum = new BigDecimal(0);
        for (BasketItem item :
                basket.getItems()) {
            productService.evaluatePrice(item);
            basket.setTotalPrice(basket.getTotalPrice().add(item.getPrice()));
        }
    }

}
