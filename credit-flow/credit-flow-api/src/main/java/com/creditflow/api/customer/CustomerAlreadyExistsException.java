package com.creditflow.api.customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)//Gönderilen bilgi mevcut kayıtla çakışıyor HTTP409.

public class CustomerAlreadyExistsException extends RuntimeException{
    public CustomerAlreadyExistsException(String customerNumber){
        super("Customer number already exists: " + customerNumber);

    }
}
    

