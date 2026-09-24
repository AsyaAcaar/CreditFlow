# CreditFlow

CreditFlow, müşteri, kredi ve taksit süreçlerini uçtan uca modelleyen eğitim ve portföy amaçlı bir kredi takip uygulamasıdır.

> Bu proje gerçek bir banka sistemi değildir. Projede kullanılan tüm müşteri ve finans verileri kurgusaldır; gerçek kişi veya kurum verisi içermez.

## Özellikler

- Yeni müşteri oluşturma
- Oracle sequence ile otomatik 6 haneli müşteri numarası üretme
- Oracle sequence ile benzersiz 10 haneli CreditFlow ID üretme
- Müşteriye kredi tanımlama ve uygun vade kontrolü
- Aktif kredisi bulunan müşteriye ikinci aktif kredi açılmasını engelleme
- Kredi oluşturulduğunda taksitleri otomatik üretme
- Taksitlerin sırasıyla ödenmesini zorunlu tutma
- Son taksit ödendiğinde krediyi otomatik kapatma
- REST API hata kodları ve doğrulama kuralları
- React Native/Expo ile müşteri, kredi ve taksit ekranları

## Mimari

```text
React Native / Expo
        |
        | HTTP + JSON
        v
Spring Boot REST API
        |
        | Spring Data JPA / JDBC
        v
Oracle Database Free (Docker)
```

Mobil uygulama Oracle'a doğrudan bağlanmaz. İstekler REST API üzerinden alınır; iş kuralları backend servislerinde uygulanır ve veritabanı işlemleri repository katmanı üzerinden gerçekleştirilir.

## Teknolojiler

- Java 21
- Spring Boot 4.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- Jakarta Validation
- Oracle Database Free ve Oracle SQL
- Docker Desktop
- React Native 0.86
- Expo 57
- TypeScript
- Git

## Proje yapısı

```text
credit-flow/
├── credit-flow-api/       # Spring Boot REST API
├── credit-flow-mobile/    # React Native / Expo uygulaması
├── database/              # Şema, migration, sorgu ve örnek veri dosyaları
└── learning/              # Öğrenme sürecinde hazırlanan Java örnekleri
```

## Yerel geliştirme

### Gereksinimler

- Java 21
- Node.js ve npm
- Docker Desktop
- ARM64 veya kullandığınız platformla uyumlu resmî Oracle Database Free container image'ı

### Veritabanı

Oracle container'ı yalnızca yerel bilgisayardan erişilebilecek şekilde `127.0.0.1:1521` adresine bağlanmalıdır. Yeni bir veritabanında önce `credit-flow/database/schema.sql`, istenirse ardından tamamen kurgusal kayıtlar içeren `credit-flow/database/sample-data.sql` çalıştırılır.

Mevcut eski bir CreditFlow veritabanını yeni müşteri kimliği yapısına geçirmek için `credit-flow/database/migration-v2-customer-identifiers.sql` kullanılır.

### Backend

Veritabanı parolası dosyada tutulmaz. Çalıştırmadan önce kendi yerel parolanızı ortam değişkenine girin:

```bash
cd credit-flow/credit-flow-api
read -s "CREDITFLOW_DB_PASSWORD?Oracle uygulama parolasını gir: "
export CREDITFLOW_DB_PASSWORD
./mvnw spring-boot:run
```

API varsayılan olarak `http://localhost:8080` adresinde çalışır.

### Frontend

```bash
cd credit-flow/credit-flow-mobile
npm install
npm run web
```

Expo web uygulaması varsayılan olarak `http://localhost:8081` adresinde açılır.

## Temel API uçları

| Method | Yol | Açıklama |
|---|---|---|
| `GET` | `/api/customers` | Müşterileri listeler |
| `POST` | `/api/customers` | Yeni müşteri oluşturur |
| `GET` | `/api/loans` | Kredileri listeler |
| `POST` | `/api/loans` | Yeni kredi oluşturur |
| `GET` | `/api/loans/{loanId}/installments` | Kredi taksitlerini listeler |
| `PATCH` | `/api/installments/{id}/pay` | Sıradaki taksidi öder |

## Güvenlik notları

- Proje yalnızca eğitim ve yerel geliştirme amacıyla hazırlanmıştır.
- Gerçek müşteri, kimlik, kredi veya kurum verisi kullanılmaz.
- Veritabanı parolası Git'e eklenmez; ortam değişkeninden okunur.
- Oracle bağlantısı `127.0.0.1` ile yerel erişime sınırlandırılır.
- Uygulamada kimlik doğrulama ve yetkilendirme bulunmadığından internete açık üretim sistemi olarak kullanılmamalıdır.
