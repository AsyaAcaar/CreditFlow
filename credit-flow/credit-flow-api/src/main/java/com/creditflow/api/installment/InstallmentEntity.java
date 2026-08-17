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
import jakarta.persistence.SequenceGenerator;

    @Entity
    @Table(name="installments")

    

public class InstallmentEntity {
 protected InstallmentEntity(){


    }
    //kredi oluştururken bizim de yeni taksit nesneleri üretmemiz gerekiyor. Bu nedenle ayrıca public constructor yazacağız.

    public InstallmentEntity(Long loanId,Integer installmentNumber,BigDecimal amount ,LocalDate dueDate ){
        this.loanId=loanId;
        this.installmentNumber=installmentNumber;
        this.amount=amount;
        this.dueDate=dueDate;
        this.status=InstallmentStatus.PENDING;


    }
    @Id
    @SequenceGenerator( name ="installments_seq_generator", //name Java tarafındaki bağlantı ismidir; sequenceName Oracle’daki gerçek sequence ismidir.
        sequenceName = "installments_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installments_seq_generator")
   
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
