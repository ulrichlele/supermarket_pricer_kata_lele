package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.utils.AmountUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
@Service
public class BasketService {

    private ProductService productService;

    private static final MathContext PRICE_ROUNDING  = new MathContext(2);

    public BasketService(ProductService productService){
        this.productService = productService;
    }

    public void evaluatePrice(Basket basket) throws IncompatibleUnitsException {
        for (BasketItem item : basket.getItems()) {
            productService.evaluatePrice(item);
            basket.setTotalPrice(basket.getTotalPrice().add(item.getTotalPrice()));
        }
        basket.setTotalPrice(AmountUtil.scaleAmount(basket.getTotalPrice()));
    }

}
