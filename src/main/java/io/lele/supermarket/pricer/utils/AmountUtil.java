package io.lele.supermarket.pricer.utils;

import io.lele.supermarket.pricer.model.Contants;

import java.math.BigDecimal;
import java.math.MathContext;

public class AmountUtil {

    public static BigDecimal scaleAmount(BigDecimal amount){
        BigDecimal scaled = null;
        if(amount != null){
            if (amount.scale() > 2)
                amount = amount.round(new MathContext(3));
            scaled = amount.setScale(Contants.PRICE_SCALE);
        }
        return scaled;
    }
}
