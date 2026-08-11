package com.creditflow.api.loan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidLoanTermException extends RuntimeException{
        public InvalidLoanTermException(Integer termMonths){
        super( "Invalid loan term: " + termMonths
    + ". Allowed terms: 6, 12, 24, 36"
);

    }

}
