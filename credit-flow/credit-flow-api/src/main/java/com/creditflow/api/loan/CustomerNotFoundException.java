package com.creditflow.api.loan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)//Gönderilen bilgi bulunamadı.


public class CustomerNotFoundException extends RuntimeException{
        public CustomerNotFoundException(Long customerId){
        super("Customer not found: " + customerId);

    }

}
