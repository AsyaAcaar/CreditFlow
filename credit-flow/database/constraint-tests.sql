-- Test 1: Olmayan bir musteriye kredi eklenememeli.Customer id hatalidir burada.
INSERT INTO loans (loan_number,customer_id,principal_amount,term_months)
VALUES ('TEST-INVALID-FK',
99999,
5000,
12);

SELECT COUNT(*) FROM LOANS WHERE loan_number='TEST-INVALID-FK';
--Test 2:Geçerli olmayan bir vadede kredi verilmemeli.IN içerisinde bulunan vadelerden biri seçilmeli.
INSERT INTO loans (loan_number,customer_id,principal_amount,term_months)
VALUES ('TEST-INVALID-TERM',
(SELECT id FROM CUSTOMERS WHERE customer_number='TEST-C1001'),
5000,
18);

SELECT COUNT(*) FROM LOANS WHERE loan_number='TEST-INVALID-TERM';

--  Test 3:UNIQUE olan bir değeri test edicez.benzersiz olan bir şey tekrardan kullanılamaz.

INSERT INTO customers (customer_number,full_name)
VALUES ('TEST-C1001',
'Baska Test Musteri');


