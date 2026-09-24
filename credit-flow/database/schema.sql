CREATE SEQUENCE customers_seq --burada müsteriye uygun benzersiz bir id üretiyoruz.
START WITH 1
INCREMENT BY 1
NOCYCLE;

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

CREATE SEQUENCE loans_seq
START WITH 1
INCREMENT BY 1
NOCYCLE;

CREATE SEQUENCE installments_seq
START WITH 1
INCREMENT BY 1
NOCYCLE;


CREATE TABLE customers(
    id NUMBER PRIMARY KEY,
    customer_number VARCHAR2(6) NOT NULL UNIQUE,
    creditflow_id NUMBER(10) NOT NULL UNIQUE,
    CONSTRAINT ck_customers_customer_number CHECK (REGEXP_LIKE(customer_number, '^[0-9]{6}$')),
    CONSTRAINT ck_customers_creditflow_id CHECK (creditflow_id BETWEEN 1000000000 AND 9999999999),
    full_name VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL

);

CREATE TABLE loans(
    id NUMBER PRIMARY KEY,
    loan_number VARCHAR2(20) NOT NULL UNIQUE,
    customer_id NUMBER NOT NULL ,
    CONSTRAINT fk_loans_customer 
    FOREIGN KEY(customer_id)
    REFERENCES customers(id),
    principal_amount NUMBER(15,2) NOT NULL CHECK(principal_amount>0),
    term_months NUMBER NOT NULL CHECK (term_months IN (6,12,24,36)),
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL CHECK (status IN ('ACTIVE','CLOSED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL

);

CREATE TABLE installments(
    id NUMBER PRIMARY KEY,
    loan_id NUMBER NOT NULL,
    CONSTRAINT fk_installments_loan
    FOREIGN KEY (loan_id)
    REFERENCES loans(id),
    installment_number NUMBER NOT NULL CHECK(installment_number>0),
    amount NUMBER(15,2) NOT NULL CHECK(amount>0),
    due_date DATE NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL CHECK(status IN ('PENDING','PAID','OVERDUE')),
    paid_at TIMESTAMP ,
    CONSTRAINT uq_installments_loan_number  UNIQUE(loan_id,installment_number)

);
--foreign key ile iki tablo arasında gerçek ve kontrol edilen bir bağlantı kurduk.--foreign key ile iki tablo arasında gerçek ve kontrol edilen bir bağlantı kurduk.virgülsüz