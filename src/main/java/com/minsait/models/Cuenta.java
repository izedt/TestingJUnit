package com.minsait.models;

import com.minsait.exceptions.DineroInsuficienteException;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data //otdas y tostring //no siempre se usa data porq string expone los datos
public class Cuenta {
    @NonNull
    private String persona;
    @NonNull
    private BigDecimal saldo;
    private Banco banco;



    public void  retirar( BigDecimal monto){
        BigDecimal saldoAux = this.saldo.subtract(monto);
        if(saldoAux.compareTo(BigDecimal.ZERO)<0)throw new DineroInsuficienteException("Dinero Insuficiente");

        this.saldo=saldoAux;
    }

    public void despositar (BigDecimal monto){
        this.saldo=saldo.add(monto);
    }

}
