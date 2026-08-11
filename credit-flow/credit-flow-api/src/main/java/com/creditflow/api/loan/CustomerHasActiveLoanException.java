
package com.creditflow.api.loan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)


public class CustomerHasActiveLoanException extends RuntimeException{
        public CustomerHasActiveLoanException(Long customerId){
        super("Customer already has an active loan: " + customerId);

    }

}
