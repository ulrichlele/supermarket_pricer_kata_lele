package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.domain.Basket;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import org.springframework.stereotype.Service;

import java.math.MathContext;
@Service
public class BasketService {

    private BasketItemService basketItemService;

    private static final MathContext PRICE_ROUNDING  = new MathContext(2);

    public BasketService(BasketItemService basketItemService){
        this.basketItemService = basketItemService;
    }

    public void evaluatePrice(Basket basket) throws IncompatibleUnitsException {
        for (BasketItem item : basket.getItems()) {
            basketItemService.evaluatePrice(item);
            basket.setTotalPrice(basket.getTotalPrice().add(item.getTotalPrice()));
        }
        basket.setTotalPrice(AmountUtil.scaleAmount(basket.getTotalPrice()));
    }

}
