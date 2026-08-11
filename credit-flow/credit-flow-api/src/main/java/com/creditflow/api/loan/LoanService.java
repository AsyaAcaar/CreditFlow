package com.creditflow.api.loan;
import org.springframework.stereotype.Service;
import com.creditflow.api.customer.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import com.creditflow.api.customer.CustomerEntity;
import java.util.Optional;
import java.util.List;

@Service
public class LoanService {
    private static final Set<Integer> PERMITTED_LOAN_MONTHS=
        Set.of(6,12,24,36);


    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    public LoanService(LoanRepository loanRepository ,
        CustomerRepository customerRepository
    ){


        this.loanRepository=loanRepository;
        this.customerRepository=customerRepository;

    }//kredileri listeleme methodu başlıyor.
     @Transactional(readOnly=true)
        public List<LoanResponse>getAll(){
            return loanRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
        }
    @Transactional//asıl kredi oluşturma methodu başlıyor.
        public LoanResponse create (CreateLoanRequest request){ //create burada metor adıdır.
            checkTermMonths(request.getTermMonths());
            checkLoanNumber(request.getLoanNumber());
            CustomerEntity customer=findCustomer(request.getCustomerId());
            checkActiveLoan(request.getCustomerId());
            LoanEntity loan = new LoanEntity( //constructor çağırılırken veri tipleri tekrar yazılmaz;gerçek değerler gönderilirç
            request.getLoanNumber(),
            customer,
            request.getPrincipalAmount(),
            request.getTermMonths()
    );
            LoanEntity savedLoan =loanRepository.save(loan);//nesneyi Oracle a kaydediyoruz.
            return toResponse(savedLoan);



        }
    private void checkTermMonths(Integer termMonths){ //vade süresi kontrolü kuralı.
            if (!PERMITTED_LOAN_MONTHS.contains(termMonths)){
                throw new InvalidLoanTermException(termMonths);
            }
        }

        private void checkLoanNumber(String loanNumber){//kredi numarası kontrolü kuralı.
            if(loanRepository.existsByLoanNumber(loanNumber)){//metot zaten boolean döndürür.true ise if bloğuna gir false ise if bloğunu geç.
                throw new LoanAlreadyExistsException(loanNumber);
            }
        }
        private CustomerEntity findCustomer(Long customerId){//müşteri var mı kontrolü kuralı.
            Optional<CustomerEntity> customer=
            customerRepository.findById(customerId);
            if (customer.isEmpty()){
                throw new CustomerNotFoundException(customerId);
            }
            return customer.get();
        }

        private void checkActiveLoan(Long customerId){
            if(loanRepository.existsByCustomer_IdAndStatus(
                customerId,
                LoanStatus.ACTIVE
            )) {
                throw new CustomerHasActiveLoanException(customerId);
            }
        }
        private LoanResponse toResponse(LoanEntity loan){
            return new LoanResponse(
                loan.getId(),
                loan.getLoanNumber(),
                loan.getCustomer().getId(),
                loan.getCustomer().getFullName(),
                loan.getPrincipalAmount(),
                loan.getTermMonths(),
                loan.getStatus()
            );
        }




}
