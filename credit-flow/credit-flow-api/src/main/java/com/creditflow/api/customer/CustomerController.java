package com.creditflow.api.customer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

// Bu sınıf HTTP isteklerini karşılar ve sonuçları JSON olarak döndürür.

@RestController

@RequestMapping("/api/customers")

//@RequestMapping("/api/customers")
//+
//@PostMapping
//=
//POST /api/customers


// Bu Controller'daki bütün endpoint'lerin temel adresi.
// @RequestMapping("/api/customers")

public class CustomerController {
    private final CustomerService customerService;

    // Spring oluşturduğu CustomerService object'ini buraya verir.
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;// burası bir dependency injection örneğidir.library kullanacağı
                                               // repository yi kendi oluşşturmayacak;dışarıdan hazır alacak.
    }

    @GetMapping

    // GET /api/customers isteği geldiğinde bu metot çalışır.@GetMapping
    public List<CustomerResponse> getAll() {

        // Controller işi kendisi yapmaz, Service'e yönlendirir.
        return customerService.getAll();
    }
    @ResponseStatus(HttpStatus.CREATED) //Bu metot başarıyla tamamlanırsa HTTP cevabının durum kodu 201 olsun.

@PostMapping //“POST isteği geldiğinde hemen altımdaki create metodunu çalıştır.”
    public CustomerResponse create (@Valid @RequestBody CreateCustomerRequest request) {//Valid in sebebi:Bu request nesnesini Service’e göndermeden önce üzerindeki validation kurallarını çalıştır.
        return customerService.create(request);
    }// Controller create metodu request’i alır
     // CustomerService create metoduna gönderir
     // Service’in ürettiği CustomerResponse’u döndürür

}
