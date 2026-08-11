package com.creditflow.api.customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.SequenceGenerator;

@Entity //JPA tarafından yönetilen veritabanı sınıfıdır.
@Table(name = "customers") //bu sınıfı Oracle daki customers tablosuyla eşleştir.

public class CustomerEntity {
    @Id //JPA ya bu field ın primary key olduğunu söyler.
    @SequenceGenerator(
    name = "customers_seq_generator",
    sequenceName = "customers_seq",
    allocationSize = 1
)
@GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "customers_seq_generator"
)

    @Column(name = "id")//javadaki "id" fieldını Oracle daki ID kolonuyla eşler.
    private Long id;

    @Column(
    name = "customer_number",
    nullable = false,
    unique = true,
    length = 20)
    private String customerNumber;

    @Column(
    name = "full_name",
    nullable = false,
    length = 100)
    private String fullName;

    @Column(
        name="created_at",
        nullable=false,
        insertable=false,
        updatable=false
    )
    private LocalDateTime createdAt;

    protected CustomerEntity(){

    }
    public CustomerEntity(String customerNumber,String fullName){
        this.customerNumber=customerNumber;
        this.fullName=fullName;
    }
    public Long getId(){
        return id;
    }
    public String getCustomerNumber(){
        return customerNumber;
    }
    public String getFullName(){
        return fullName;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }




}
