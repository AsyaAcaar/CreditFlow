package com.creditflow.api.customer;
import java.util.List;

import org.springframework.stereotype.Service;

// Müşterilerle ilgili iş akışlarını yönetir.
@Service
public class CustomerService {

        private final CustomerRepository customerRepository;
        public CustomerService(CustomerRepository customerRepository){
          this.customerRepository=customerRepository;  
        }

    public List<CustomerResponse>getAll(){ 
        // Repository henüz olmadığı için sahte veriler burada tutuluyordu.fakat şimdi gerçek bilgileri ekleyeceğiz.
        return customerRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
} 
        
    
    public CustomerResponse create(CreateCustomerRequest request) {
        String customerNumber = customerRepository.getNextCustomerNumber();
        Long creditFlowId = customerRepository.getNextCreditFlowId();

        CustomerEntity customer = new CustomerEntity(customerNumber, creditFlowId, request.getFullName());
        CustomerEntity savedCustomer = customerRepository.save(customer);
        return toResponse(savedCustomer);
    }

    private CustomerResponse toResponse(CustomerEntity customer){
        return new CustomerResponse(
            customer.getId(),
            customer.getCustomerNumber(),
            customer.getCreditFlowId(),
            customer.getFullName()
        );
    }
}
