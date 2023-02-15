package io.lele.supermarket.pricer.core;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse<T> implements Serializable {

    private boolean success = false;

    private  T data;

    private String message;

    private Set<ValidatorResult> results;


}
