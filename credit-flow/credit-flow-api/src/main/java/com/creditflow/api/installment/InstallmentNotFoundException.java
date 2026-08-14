package com.creditflow.api.installment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstallmentNotFoundException extends RuntimeException{
        public InstallmentNotFoundException(Long installmentId){
        super("There is no installment with this ID number : " + installmentId);

    }
    
}
