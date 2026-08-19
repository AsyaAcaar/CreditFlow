package com.creditflow.api.installment;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.creditflow.api.loan.LoanRepository;
import org.springframework.stereotype.Service;
import com.creditflow.api.loan.LoanEntity;
import com.creditflow.api.loan.LoanNotFoundException;

@Service

public class InstallmentService {
    private final InstallmentRepository installmentRepository;
    private final LoanRepository loanRepository;

    public InstallmentService(
            InstallmentRepository installmentRepository,
            LoanRepository loanRepository) {
        this.installmentRepository = installmentRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public List<InstallmentResponse> getByLoanId(Long loanId) {
        return installmentRepository
                .findByLoanIdOrderByInstallmentNumberAsc(loanId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private InstallmentResponse toResponse(InstallmentEntity installment) {
        return new InstallmentResponse(
                installment.getId(),
                installment.getLoanId(),
                installment.getInstallmentNumber(),
                installment.getAmount(),
                installment.getDueDate(),
                installment.getStatus(),
                installment.getPaidAt());

    }

    private InstallmentEntity findInstallment(Long installmentId) {
        return installmentRepository.findById(installmentId)
                .orElseThrow(() -> new InstallmentNotFoundException(installmentId));

    }

    @Transactional
    public InstallmentResponse pay(Long installmentId) {
        InstallmentEntity installment = findInstallment(installmentId);
        if (installment.getStatus() == InstallmentStatus.PAID) {

            throw new InstallmentAlreadyPaidException(installmentId);
        }
        boolean hasUnpaidPreviousInstallment = installmentRepository
                .existsByLoanIdAndInstallmentNumberLessThanAndStatusNot(
                        installment.getLoanId(),
                        installment.getInstallmentNumber(),
                        InstallmentStatus.PAID);

        if (hasUnpaidPreviousInstallment) {
            throw new PreviousInstallmentUnpaidException(installmentId);

        }

        installment.markPaid(LocalDateTime.now());
        boolean hasUnpaidInstallment = installmentRepository.existsByLoanIdAndStatusNot(installment.getLoanId(),
                InstallmentStatus.PAID);
        if (!hasUnpaidInstallment) {
            LoanEntity loan = loanRepository.findById(installment.getLoanId())
                    .orElseThrow(() -> new LoanNotFoundException(installment.getLoanId()));
            long installmentCount = installmentRepository.countByLoanId(installment.getLoanId());
            if (installmentCount == loan.getTermMonths()) {
                loan.close();
            }
        } // transactional sayesinde değişiklik Oracle 'a otomatik kaydedilir.
        return toResponse(installment);
    }

}
