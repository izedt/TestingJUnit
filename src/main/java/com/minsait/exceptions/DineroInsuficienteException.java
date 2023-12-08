package com.minsait.exceptions;

import com.minsait.models.Cuenta;
import lombok.NonNull;

import java.math.BigDecimal;

public class DineroInsuficienteException extends RuntimeException{

    public DineroInsuficienteException (String message){
        super(message);
    }
}
