INSERT INTO customers (id,customer_number,full_name)
VALUES(
    customers_seq.NEXTVAL,
    'TEST-C1001',
    'Test Musteri Bir'
);

INSERT INTO loans(id,loan_number,customer_id,principal_amount,term_months)
VALUES(
    loans_seq.NEXTVAL,
    'TEST-L1001',
    (SELECT id FROM customers WHERE customer_number='TEST-C1001'),
    120000.00,
    12
);

INSERT INTO installments(loan_id,installment_number,amount,due_date)
VALUES(
    (SELECT id FROM loans WHERE loan_number='TEST-L1001'),
    1,
    10000.00,
    DATE '2026-09-01'

);
--şimdi kişiye bağlı olarak krediyi bağlıyoruz.yine parantez içinde detaylar belirtilmeli.
--burada parantez içinde sırasıyla vereceğimiz kelimeleri belirtme sebebimiz alnızca iki değer verdiğin için “not enough values” hatası oluşur.çünkü normalade 4 adet parametre bulunuyor.
