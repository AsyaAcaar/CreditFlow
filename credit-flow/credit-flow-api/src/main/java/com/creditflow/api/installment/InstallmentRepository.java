package com.creditflow.api.installment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstallmentRepository

extends JpaRepository<InstallmentEntity,Long>{
    List<InstallmentEntity> findByLoanIdOrderByInstallmentNumberAsc(Long loanId);

}