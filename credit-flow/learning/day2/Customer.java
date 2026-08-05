package learning.day2;

public class Customer {
    private Long id;
    private String customerNumber;
    private String fullName;//fields

    public Customer(Long id,String customerNumber,String fullName){//constructor
        this.id=id;
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
    public String getDisplayName(){
        return customerNumber+" - "+fullName ;
    }
    public void updateFullName(String newFullName){
        this.fullName=newFullName;
    }


}
