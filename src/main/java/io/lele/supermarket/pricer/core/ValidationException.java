package io.lele.supermarket.pricer.core;

import java.util.Set;

public class ValidationException extends RuntimeException{

    private Set<ValidatorResult> validatorResults;

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(String message, Set<ValidatorResult> validatorResults){
        super(message);
        this.validatorResults = validatorResults;
    }

}
