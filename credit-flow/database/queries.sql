SELECT * FROM customers ; ---SELECT hangi sütunlar FROM hangi tablo WHERE koşul
SELECT loan_number ,principal_amount ,status FROM loans WHERE customer_id =1;

--join yapısına ihitiyacımız var join iki tablonun bilgilerini geçici olarrak yan yana getirir.
SELECT c.id, l.customer_id,c.full_name,l.loan_number,l.principal_amount,l.status
FROM customers c
JOIN loans l
ON c.id=l.customer_id;

SELECT c.full_name,l.loan_number,i.installment_number,i.amount,i.status
FROM customers c 
JOIN loans l
ON c.id=l.customer_id
JOIN installments i
ON l.id=i.loan_id;