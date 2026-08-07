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


if (customerRepository.existsByCustomerNumber(request.getCustomerNumber())){
        throw new CustomerAlreadyExistsException(request.getCustomerNumber());
    }// request.getCustomerNumber(): Frontend’den gelen müşteri numarasını okur.
        // existsByCustomerNumber(...): Bu numara Oracle’da var mı diye Repository üzerinden sorgular.
        // Sonuç true ise if bloğuna girilir.
 // new: Yeni bir exception nesnesi oluşturur.
        // Exception’a hangi müşteri numarasının tekrarlandığını gönderir.
        // throw: Metodu burada durdurur; aşağıdaki save işlemi çalışmaz.
    


        CustomerEntity customer=new CustomerEntity(request.getCustomerNumber(),request.getFullName());//requestten gelen iki değerle bellekte Entity oluşturulur.
            CustomerEntity savedCustomer=customerRepository.save(customer); //save(customer) Entity’yi Oracle’a kaydeder.
            return toResponse(savedCustomer); //Oracle’ın ürettiği ID’yi taşıyan savedCustomer, API Response’una çevrilir.
        }
        private CustomerResponse toResponse(CustomerEntity customer){
            return new CustomerResponse(customer.getId(), customer.getCustomerNumber(), customer.getFullName());
        }

}
