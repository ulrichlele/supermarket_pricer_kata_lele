package io.lele.supermarket.pricer.core;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"message", "clazz", "field", "parameters"})
@EqualsAndHashCode(of = {"message", "clazz", "field"})
public class ValidatorResult {

    private String message;
    private String clazz;
    private String field;
    private Object[] parameters = new Object[] {};

    public ValidatorResult(String message, String clazz, String field) {
        this.message = message;
        this.clazz = clazz;
        this.field = field;
    }

    public ValidatorResult(String message, Class clazz, String field) {
        this.message = message;
        this.clazz = clazz.getCanonicalName();
        this.field = field;
    }

}
