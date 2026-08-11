package com.creditflow.api.loan;

import jakarta.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.creditflow.api.customer.CustomerEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "loans") // hangi tablo

public class LoanEntity {
    protected LoanEntity() { // protected çünkü uygulamanın boş kredi vermesini engellemek için .
    }

    @Id
    @SequenceGenerator(name = "loans_seq_generator", sequenceName = "loans_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loans_seq_generator")
    @Column(name = "id") // hangi kolon
    private Long id;

    @Column(name = "loan_number", nullable = false, unique = true, length = 20)
    private String loanNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // birçok kredi tek bir müşteriye bağlı olabilir.
    @JoinColumn(name = "customer_id", nullable = false) // bu iki tabloyu foreign key kolonu üzeirnden birleştir.
    private CustomerEntity customer;

    @Column(name = "principal_amount", nullable = false, precision = 15, // toplam basamak sayısıdır.
            scale = 2 // virgülden sonraki basamak sayısıdır.
    )
    private BigDecimal principalAmount;

    @Column(name = "term_months", nullable = false

    )
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LoanStatus status;

    @Column(name = "created_at", nullable = false, updatable = false, // sonradan güncellenemez
            insertable = false// tarih default gelecek sonrasında java tarafından yazılmayacak.
    )
    private LocalDateTime createdAt;

    public LoanEntity(
            String loanNumber,
            CustomerEntity customer,
            BigDecimal principalAmount,
            Integer termMonths ) {

        this.loanNumber = loanNumber;// aldığımız number vs dataları buradaki parametrelerle eşliyoruz.
        this.customer = customer; // içerideki her parametreyi kendi field'ına atıyoruz.
        this.principalAmount = principalAmount;
        this.termMonths = termMonths;
        this.status = LoanStatus.ACTIVE; //default olarak active değerini almalı.loan status'ten
        // active değerini istedik.
        //default olarak databaseden alacak tarih değerini.
    }
    public String getLoanNumber(){
        return loanNumber;
    }
     public Long getId(){
        return id;
    }
    public CustomerEntity getCustomer(){
        return customer;
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
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    //bunların hiçbiri dışarıdan ulaşılıp değiştirilmemeli.

}
