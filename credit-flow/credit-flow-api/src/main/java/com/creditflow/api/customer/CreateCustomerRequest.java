package com.creditflow.api.customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class CreateCustomerRequest {
    @NotBlank
    @Size(max=100)
    private String fullName;

    public String getFullName(){//erişim belirleyici olarak hepsinde public yazmalıyız çünkü Spring ve farklı paketlerdeki sınıflar DTO’ya erişebilir.
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }












}
