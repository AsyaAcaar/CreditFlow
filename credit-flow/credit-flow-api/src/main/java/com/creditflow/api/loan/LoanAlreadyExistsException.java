package com.creditflow.api.loan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)

public class LoanAlreadyExistsException extends RuntimeException{
        public LoanAlreadyExistsException(String loanNumber){
        super("Loan number already exists: " + loanNumber);

    }

}