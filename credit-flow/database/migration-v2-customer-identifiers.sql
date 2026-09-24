--BURADA ZATEN ÇALIŞAN MEVCUT VERİTABANINI ESKİ YAPIDAN YENİ YAPIYA ÇEVİRİYORUZ.
CREATE SEQUENCE customer_number_seq
START WITH 100000
INCREMENT BY 1
MAXVALUE 999999
NOCYCLE
NOCACHE;

CREATE SEQUENCE creditflow_identity_seq
START WITH 1000000000
INCREMENT BY 1
MAXVALUE 9999999999
NOCYCLE
NOCACHE;

ALTER TABLE customers
ADD creditflow_id NUMBER(10);

UPDATE customers
SET customer_number =
TO_CHAR(customer_number_seq.NEXTVAL,'FM000000'), --sayıyı boşluksuz, 6 karakterlik müşteri numarasına çevirir.
creditflow_id=
creditflow_identity_seq.NEXTVAL
WHERE creditflow_id IS NULL;

COMMIT;
--BENZERSİZLİK KURALLARI BURADA BAŞLIYOR.
ALTER TABLE customers
MODIFY customer_number VARCHAR2(6);

ALTER TABLE customers
MODIFY creditflow_id NUMBER(10) NOT NULL;

ALTER TABLE customers
ADD CONSTRAINT uq_customers_creditflow_id
UNIQUE (creditflow_id);

ALTER TABLE customers
ADD CONSTRAINT ck_customers_customer_number
CHECK (REGEXP_LIKE(customer_number, '^[0-9]{6}$'));

ALTER TABLE customers
ADD CONSTRAINT ck_customers_creditflow_id
CHECK (creditflow_id BETWEEN 1000000000 AND 9999999999);
