package com.creditflow.api.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(
        value = "SELECT TO_CHAR(customer_number_seq.NEXTVAL, 'FM000000') FROM dual",
        nativeQuery = true
    )
    String getNextCustomerNumber();

    @Query(
        value = "SELECT creditflow_identity_seq.NEXTVAL FROM dual",
        nativeQuery = true
    )
    Long getNextCreditFlowId();
}
