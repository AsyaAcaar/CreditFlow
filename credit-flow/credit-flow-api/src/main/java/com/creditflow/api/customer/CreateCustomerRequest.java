package com.creditflow.api.customer;
import jakarta.validation.constraints.*;


public class CreateCustomerRequest {

    @NotBlank
    @Size(max=20)
    private String customerNumber;

    @NotBlank
    @Size(max=100)
    private String fullName;

    public String getCustomerNumber(){//getter bir metot döndürür o yüzden veri tipi ve return string.
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber){//setter bir metot döndürmez o yüzden void return type alır.
        this.customerNumber=customerNumber;

    }

    public String getFullName(){//erişim belirleyici olarak hepsinde public yazmalıyız çünkü Spring ve farklı paketlerdeki sınıflar DTO’ya erişebilir.
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }












}
