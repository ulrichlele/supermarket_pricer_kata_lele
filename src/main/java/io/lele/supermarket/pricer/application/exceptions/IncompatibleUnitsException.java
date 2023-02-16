package io.lele.supermarket.pricer.application.exceptions;

import io.lele.supermarket.pricer.core.BaseAppException;

public class IncompatibleUnitsException extends BaseAppException {
    public IncompatibleUnitsException(String message){
        super(message);
    }
}
