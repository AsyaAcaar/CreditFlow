package com.creditflow.api.customer;
import java.util.List;

import org.springframework.stereotype.Service;

// Müşterilerle ilgili iş akışlarını yönetir.
@Service
public class CustomerService {
    public List<CustomerResponse> getAll(){
        // Repository henüz olmadığı için sahte veriler burada tutuluyor.

        return List.of(
            new CustomerResponse(
           1L,
            "C1001",
            "Ayşe Demir"
        ),
        new CustomerResponse(
            2L,
            "C1002",
            "Mehmet Kaya"
        )
        );

    }
    public CustomerResponse create(CreateCustomerRequest request) {
            return new CustomerResponse(3L, request.getCustomerNumber(), request.getFullName());
        }

}
