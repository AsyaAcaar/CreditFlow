package com.creditflow.api.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository 
    
 extends JpaRepository<CustomerEntity, Long> {
    boolean existsByCustomerNumber(String customerNumber);// bu aynı müşteri numarasından tekrar başkasına açılmak isterse diye kontrol etmemizi sağlar.varsa true yoksa false döner.

}
