-- Yalnizca yerel gelistirme icin kullanilan tamamen kurgusal ornek veriler.
INSERT INTO customers (id, customer_number, creditflow_id, full_name)
VALUES(
    customers_seq.NEXTVAL,
    TO_CHAR(customer_number_seq.NEXTVAL, 'FM000000'),
    creditflow_identity_seq.NEXTVAL,
    'Test Musteri Bir'
);

INSERT INTO loans(id, loan_number, customer_id, principal_amount, term_months)
VALUES(
    loans_seq.NEXTVAL,
    'TEST-L1001',
    (SELECT id FROM customers WHERE full_name = 'Test Musteri Bir'),
    120000.00,
    12
);

INSERT INTO installments(id, loan_id, installment_number, amount, due_date)
VALUES(
    installments_seq.NEXTVAL,
    (SELECT id FROM loans WHERE loan_number='TEST-L1001'),
    1,
    10000.00,
    DATE '2026-09-01'
);

COMMIT;
