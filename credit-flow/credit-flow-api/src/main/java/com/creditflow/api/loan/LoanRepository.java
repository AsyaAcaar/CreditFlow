package com.creditflow.api.loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository
    extends JpaRepository<LoanEntity,Long>{
            boolean existsByLoanNumber(
                String loanNumber);
                boolean existsByCustomer_IdAndStatus(
                    Long customerId,
                    LoanStatus status
            );


    }
