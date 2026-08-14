package com.creditflow.api.installment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class InstallmentResponse {
    private final Long id;
    private final Long loanId;
    private final Integer installmentNumber;
    private final BigDecimal amount;
    private final LocalDate dueDate;
    private final InstallmentStatus status;
    private final LocalDateTime paidAt;
    

public InstallmentResponse(Long id, Long loanId, Integer installmentNumber,BigDecimal amount,LocalDate dueDate,InstallmentStatus status,LocalDateTime paidAt){
    this.id=id;
    this.loanId=loanId;
    this.installmentNumber=installmentNumber;
    this.amount=amount;
    this.dueDate=dueDate;
    this.status=status;
    this.paidAt=paidAt;
}
 public Long getId(){
        return id;
    }
    public Long getLoanId(){
        return loanId;
    }
    public Integer getInstallmentNumber(){
        return installmentNumber;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public InstallmentStatus getStatus(){
        return status;
    }
    public LocalDateTime getPaidAt(){
        return paidAt;
    }

}
