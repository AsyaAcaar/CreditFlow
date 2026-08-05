INSERT INTO customers (customer_number,full_name)--burada parantez içinde sırasıyla vereceğimiz kelimeleri belirtme sebebimiz alnızca iki değer verdiğin için “not enough values” hatası oluşur.çünkü normalade 4 adet parametre bulunuyor.
VALUES(
    'C1001',
    'Ayşe Demir'
);

INSERT INTO loans(loan_number,customer_id,principal_amount,term_months)--şimdi kişiye bağlı olarak krediyi bağlıyoruz.yine parantez içinde detaylar belirtilmeli.
VALUES(
    'L1001',
    1,
    120000.00,
    12
);
INSERT INTO installments(loan_id,installment_number,amount,due_date)
VALUES(
    1,
    1,
    10000.00,
    DATE '2026-09-01'

);
