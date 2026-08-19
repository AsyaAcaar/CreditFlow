package com.creditflow.api.installment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstallmentRepository

extends JpaRepository<InstallmentEntity,Long>{
    List<InstallmentEntity> findByLoanIdOrderByInstallmentNumberAsc(Long loanId);
    boolean existsByLoanIdAndInstallmentNumberLessThanAndStatusNot(Long loanId,Integer installmentNumber,InstallmentStatus status);
    boolean existsByLoanIdAndStatusNot(Long loanId,InstallmentStatus status);
    long countByLoanId(Long loanId);

}