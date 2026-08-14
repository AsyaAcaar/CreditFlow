package com.creditflow.api.installment;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service

public class InstallmentService {
    private final InstallmentRepository installmentRepository;
                public InstallmentService(InstallmentRepository installmentRepository){
                    this.installmentRepository=installmentRepository;

                }   

 @Transactional(readOnly = true)
public List<InstallmentResponse> getByLoanId(Long loanId) {
    return installmentRepository
        .findByLoanIdOrderByInstallmentNumberAsc(loanId)
        .stream()
        .map(this::toResponse)
        .toList();
}
                private InstallmentResponse toResponse(InstallmentEntity installment){
                    return new InstallmentResponse(
                        installment.getId(), 
                        installment.getLoanId(), 
                        installment.getInstallmentNumber(), 
                        installment.getAmount(), 
                        installment.getDueDate(), 
                        installment.getStatus(), 
                        installment.getPaidAt()
                    );

                } 
                private InstallmentEntity findInstallment(Long installmentId) {
                    return installmentRepository.findById(installmentId).orElseThrow(() -> new InstallmentNotFoundException(installmentId));
                

                }
                @Transactional
                public InstallmentResponse pay(Long installmentId){
                    InstallmentEntity installment=findInstallment(installmentId);
                    if(installment.getStatus()==InstallmentStatus.PAID){
                        throw new InstallmentAlreadyPaidException(installmentId);
                    }
                    installment.markPaid(LocalDateTime.now());
                    return toResponse(installment);
                }
                
                
}
