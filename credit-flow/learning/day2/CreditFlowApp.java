package learning.day2;

public class CreditFlowApp {
    public static void main(String[] args){//starting point
        Customer customer=new Customer(1L,"C1001","Ayşe Demir");
        System.out.println(customer.getDisplayName());
        customer.updateFullName("Ayşe Kaya");
        System.out.println(customer.getDisplayName());




    }

}
