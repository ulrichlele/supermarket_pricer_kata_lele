package io.lele.supermarket.pricer.core;

public class RecordNotFound extends RuntimeException{
    public RecordNotFound(String message){
        super(message);
    }
}
