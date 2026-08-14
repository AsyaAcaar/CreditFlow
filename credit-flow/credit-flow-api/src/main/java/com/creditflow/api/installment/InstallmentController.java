package com.creditflow.api.installment;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class InstallmentController {
    private final InstallmentService installmentService;
//Spring’in oluşturduğu InstallmentService nesnesini constructor ile Controller’a alıp, gelen HTTP isteklerini service’e iletebilmek için sakladık.
public InstallmentController(InstallmentService installmentService) {
    this.installmentService = installmentService;
}
@GetMapping("/loans/{loanId}/installments")
public List<InstallmentResponse> getByLoanId(@PathVariable Long loanId){
    return installmentService.getByLoanId(loanId);
}
@PatchMapping("/installments/{installmentId}/pay")
public InstallmentResponse pay (@PathVariable Long installmentId) {
    return installmentService.pay(installmentId);
}
    
    
}
