package com.creditflow.api.installment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class PreviousInstallmentUnpaidException extends RuntimeException{
    public  PreviousInstallmentUnpaidException(Long installmentId){
        super("Önceki taksit ödenmeden bu taksit ödenemez." + installmentId );
    }
    
}
