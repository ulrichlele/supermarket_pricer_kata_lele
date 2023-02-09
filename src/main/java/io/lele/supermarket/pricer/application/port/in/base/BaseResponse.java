package io.lele.supermarket.pricer.application.port.in.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T extends Serializable> implements Serializable {

    private boolean success;

    private  T data;

    private String message;


}
