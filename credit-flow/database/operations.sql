UPDATE installments SET

status ='PAID',
paid_at = CURRENT_TIMESTAMP

WHERE id=1;
