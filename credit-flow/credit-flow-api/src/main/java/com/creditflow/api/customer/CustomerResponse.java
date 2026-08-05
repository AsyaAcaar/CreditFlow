package com.creditflow.api.customer;

// API'nin kullanıcıya göndereceği müşteri verisinin biçimi.
public class CustomerResponse {// API nin dışarıya göndereceği müşteri verisi biçimidir.
    private final Long id;
    private final String customerNumber;
    private final String fullName;

// final: Constructor'dan sonra değer değiştirilemez.

    public CustomerResponse(
        Long id,
        String customerNumber,
        String fullName

    ){

    this.id=id;
    this.customerNumber=customerNumber;
    this.fullName=fullName;
    }

    // Spring/Jackson, field değerlerini JSON'a çevirmek için getter'ları kullanır.

    public Long getId(){
        return id;
    }

    public String getCustomerNumber(){
        return customerNumber;
    }

    public String getFullName(){
        return fullName;
    }



}
