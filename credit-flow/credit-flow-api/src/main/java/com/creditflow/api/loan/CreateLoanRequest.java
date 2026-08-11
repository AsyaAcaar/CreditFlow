package com.creditflow.api.loan;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public class CreateLoanRequest {

    @NotBlank
    @Size(max=20)
    private String loanNumber;

    public String getLoanNumber(){
        return loanNumber ;
    }
    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    @NotNull
    @Positive
    private Long customerId;

    public Long getCustomerId(){
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


    @NotNull
    @Positive
    private BigDecimal principalAmount;

    public BigDecimal getPrincipalAmount(){
        return principalAmount;
    }
    public void setPrincipalAmount(BigDecimal principalAmount){
        this.principalAmount=principalAmount;

    }

    @NotNull
    private Integer termMonths;

    public Integer getTermMonths(){
        return termMonths;
    }
    public void setTermMonths(Integer termMonths){
        this.termMonths=termMonths;
    }
}
