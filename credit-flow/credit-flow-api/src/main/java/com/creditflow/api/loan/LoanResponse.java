package com.creditflow.api.loan;
import java.math.BigDecimal;

public class LoanResponse {
    private final Long id;
    private final String loanNumber;
    private final Long customerId;
    private final String customerFullName;
    private final BigDecimal principalAmount;
    private final Integer termMonths;
    private final LoanStatus status;

    public LoanResponse(
        Long id,
        String loanNumber,
        Long customerId,
        String customerFullName,
        BigDecimal principalAmount,
        Integer termMonths,
        LoanStatus status
    ){
        this.id=id;
        this.loanNumber=loanNumber;
        this.customerId=customerId;
        this.customerFullName=customerFullName;
        this.principalAmount=principalAmount;
        this.termMonths=termMonths;
        this.status=status;
    }
    public Long getId(){
        return id;
    }
    public String getLoanNumber(){
        return loanNumber;
    }
    public Long getCustomerId(){
        return customerId;
    }
    public String getCustomerFullName(){
        return customerFullName;
    }
    public BigDecimal getPrincipalAmount(){
        return principalAmount;
    }
    public Integer getTermMonths(){
        return termMonths;
    }
    public LoanStatus getStatus(){
        return status;
    }
}
