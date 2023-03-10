package io.lele.supermarket.pricer.application.utils;

import io.lele.supermarket.pricer.application.services.Contants;

import java.math.BigDecimal;
import java.math.MathContext;

public class AmountUtil {

    public static BigDecimal scaleAmount(BigDecimal amount){
        if(amount == null)
            return null;
        BigDecimal scaled = null;
        if (amount.scale() > 2){
            amount = amount.round(new MathContext(3));
        }
        scaled = amount.setScale(Contants.PRICE_SCALE);
        return scaled;
    }
}
