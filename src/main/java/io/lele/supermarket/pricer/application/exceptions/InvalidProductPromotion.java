package io.lele.supermarket.pricer.application.exceptions;

import io.lele.supermarket.pricer.core.BaseAppException;

public class InvalidProductPromotion extends BaseAppException {
    public InvalidProductPromotion(String message){
        super(message);
    }
}
