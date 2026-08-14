package com.creditflow.api.installment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

    @Entity
    @Table(name="installments")

    

public class InstallmentEntity {
 protected InstallmentEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    @Column(
        name ="id"
    )
    private Long id;
    @Column(
        name="loan_id",
        nullable = false
    )
    private Long loanId;
    @Column(
        name="installment_number",
        nullable = false
    )
    private Integer installmentNumber;
    @Column(
        name="amount",
        nullable = false
    )
    private BigDecimal amount;
    @Column(
        name="due_date",
        nullable = false
    )
    private LocalDate dueDate;
    @Column(
        name="paid_at"
    )
    private LocalDateTime paidAt;
    @Enumerated(EnumType.STRING)//InstallmentStatus enum’unu Oracle’a sıra numarası olarak değil, PENDING, PAID, OVERDUE yazıları olarak kaydet.
    @Column(
        name="status",
        nullable = false
    )
    private InstallmentStatus status;
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
    public LocalDateTime getPaidAt(){
        return paidAt;
    }
    public InstallmentStatus getStatus(){
        return status;
    }
    public void markPaid(LocalDateTime paymentTime){
        this.status=InstallmentStatus.PAID;
        this.paidAt=paymentTime;
    

    }
}
