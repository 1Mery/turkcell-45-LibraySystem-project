# 📚 Library System (Microservices)

Bu proje, bir kütüphane yönetim sistemini **mikroservis mimarisi** ile geliştirmek için hazırlanmış bir backend uygulamasıdır.  
Kullanıcılar kitapları görüntüleyebilir, ödünç alabilir, iade edebilir, rezervasyon oluşturabilir ve gecikme durumunda ceza işlemleri yönetilebilir.

## 🏗 Kullanılan Teknolojiler

- **Java 21**
- **Spring Boot** (REST API)
- **Spring Cloud** (Config Server, Eureka, API Gateway)
- **PostgreSQL**
- **Apache Kafka** (event-driven iletişim)
- **Docker & Docker Compose**

## 🧱 Mimarî Yapı

Proje, birden fazla mikroservisten oluşmaktadır (örnek):

- `book-service` – Kitap ve kitap kopyası yönetimi
- `loan-service` – Ödünç alma ve iade işlemleri
- `reservation-service` – Rezervasyon yönetimi
- `fine-service` – Ceza hesaplama ve ödeme takibi
- `notification-service` – Bildirimlerin gönderimi
- `user-service` – Üye / kullanıcı yönetimi
- `config-server` – Merkezi konfigürasyon yönetimi
- `discovery-server (Eureka)` – Servis keşfi
- `api-gateway` – Dış dünyaya açılan tek giriş noktası

Kod yapısında **DDD (Domain-Driven Design)** prensipleri, katmanlı yapı (domain, application, infrastructure, web) ve event-driven yaklaşım kullanılmaktadır.

## 📌 Notlar

- Proje hâlâ geliştirme aşamasındadır. Yeni özellikler, iyileştirmeler ve refaktörler eklenmeye devam etmektedir.
- Amaç; mikroservis mimarisi, DDD, event-driven yapı ve altyapı araçlarını (Kafka, Docker, Spring Cloud) gerçekçi bir senaryo üzerinde uygulamaktır.
