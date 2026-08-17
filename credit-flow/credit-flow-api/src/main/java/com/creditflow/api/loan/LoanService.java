package com.creditflow.api.loan;
import org.springframework.stereotype.Service;
import com.creditflow.api.customer.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import com.creditflow.api.customer.CustomerEntity;
import java.util.Optional;
import java.util.List;
import com.creditflow.api.installment.InstallmentRepository;
import com.creditflow.api.installment.InstallmentEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;


@Service
public class LoanService {
    private static final Set<Integer> PERMITTED_LOAN_MONTHS=
        Set.of(6,12,24,36);


    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final InstallmentRepository installmentRepository;
    private void createInstallments(LoanEntity loan){//burada vadeye bağlı olarak aylık kredi tutarını BigDecimal tipindeki değerde hesaplıyoruz.
        BigDecimal regularAmount = loan.getPrincipalAmount()
        .divide(
            BigDecimal.valueOf(loan.getTermMonths()),
            2,
            RoundingMode.HALF_UP
        );
        List<InstallmentEntity> installments= new ArrayList<>();
            for (int installmentNumber=1;installmentNumber<=loan.getTermMonths();installmentNumber++){ //ArrayList taksitleri bellekte toplar; for döngüsü 1’den vade ayına kadar her taksit için bir kez çalışır.
//son taksitte kuruş farkını düzeltme mantığı.
boolean isLastInstallment =( installmentNumber == loan.getTermMonths());
    BigDecimal installmentAmount;
    if (isLastInstallment){
        //Normal taksitler yuvarlanmış tutarı alır; son taksit, önceki taksitlerden sonra kalan gerçek borcu alarak toplamı eşitler.
        BigDecimal previousInstallmentsTotal =regularAmount.multiply(BigDecimal.valueOf(loan.getTermMonths()-1));
        installmentAmount = loan.getPrincipalAmount().subtract(previousInstallmentsTotal);
    }
    else{
        installmentAmount=regularAmount;
    }
 LocalDate dueDate= LocalDate.now().plusMonths(installmentNumber);
 InstallmentEntity installment =new InstallmentEntity (loan.getId() , installmentNumber , installmentAmount , dueDate);
 installments.add(installment);
            }

            installmentRepository.saveAll(installments);
    }

    public LoanService(LoanRepository loanRepository ,
        CustomerRepository customerRepository,
        InstallmentRepository installmentRepository
    ){


        this.loanRepository=loanRepository;
        this.customerRepository=customerRepository;
        this.installmentRepository=installmentRepository;

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
            createInstallments(savedLoan);
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
