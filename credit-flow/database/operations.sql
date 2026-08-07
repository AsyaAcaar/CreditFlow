UPDATE installments SET
status ='PAID',
paid_at = CURRENT_TIMESTAMP
WHERE loan_id = (SELECT id FROM LOANS WHERE loan_number ='TEST-L1001')
AND installment_number=1
AND status='PENDING';


