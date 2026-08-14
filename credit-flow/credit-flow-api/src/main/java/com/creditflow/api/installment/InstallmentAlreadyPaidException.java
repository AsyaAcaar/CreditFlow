package com.creditflow.api.installment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class InstallmentAlreadyPaidException extends RuntimeException{
     public InstallmentAlreadyPaidException(Long installmentId){
        super("This installment already paid : " + installmentId);

    }
    
}
