package com.creditflow.api.loan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/loans")

public class LoanController {
    private final LoanService loanService;//İlk satır: Sınıfın yanında tutacağı Service field’ını tanımlar ve ; ile biter.

    public LoanController(LoanService loanService) { //Constructor: Spring’in verdiği Service nesnesini parametre olarak alır.
        this.loanService =loanService; //Constructor adı sınıf adıyla tamamen aynı olur.
    }

    @GetMapping
    public List<LoanResponse> getAll(){
        return loanService.getAll();
    }


    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping
    public LoanResponse create(@Valid @RequestBody CreateLoanRequest request){
        return loanService.create(request);
    }

}
