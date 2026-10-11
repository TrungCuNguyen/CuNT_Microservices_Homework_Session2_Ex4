# Order Service - Global Exception Handling

Dự án Spring Boot thực hiện chuẩn hóa định dạng phản hồi lỗi (Error Response Format) với `@RestControllerAdvice`, phục vụ API cho Frontend.

---

## 🛠 Công nghệ sử dụng
- **Java**: JDK 17
- **Framework**: Spring Boot 4.0.8 (Spring Framework 7)
- **Build tool**: Gradle
- **Database**: PostgreSQL (Spring Data JPA / Hibernate)
- **Cấu hình**: `application.properties`
- **Thư viện phụ trợ**: Lombok

---

## 📁 Cấu trúc dự án
Dự án được phân chia theo đúng các tầng kiến trúc yêu cầu:

```text
src/main/java/com/ecommerce/order/
├── OrderApplication.java                     # Class khởi chạy ứng dụng
├── controller/
│   └── OrderController.java                 # Tầng Controller (GET /api/v1/orders/{id})
├── service/
│   ├── OrderService.java                     # Interface nghiệp vụ
│   └── impl/
│       └── OrderServiceImpl.java            # Triển khai nghiệp vụ & ném ResourceNotFoundException
├── repository/
│   └── OrderRepository.java                 # Tầng Repository kế thừa JpaRepository
├── entity/
│   └── Order.java                           # Entity ánh xạ bảng `orders` trong PostgreSQL
├── dto/
│   ├── ApiResponseError.java                # DTO trả về khi có lỗi (timestamp, status, error, message)
│   └── OrderResponse.java                   # DTO dữ liệu đơn hàng
└── exception/
    ├── ResourceNotFoundException.java       # Custom Exception
    └── GlobalExceptionHandler.java          # @RestControllerAdvice bắt lỗi toàn cục
```

---

## ⚙️ Cấu hình cơ sở dữ liệu (`application.properties`)
File [application.properties](file:///e:/Dev/JAVA/ex4_ss2_micro/src/main/resources/application.properties):

```properties
spring.application.name=order-service
server.port=8080

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/order_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA & Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

---

## 🚀 Kiểm thử API

### 1. Kịch bản lỗi: ID không tồn tại
Gọi API lấy đơn hàng với ID không tồn tại (ví dụ: `999`):
```http
GET http://localhost:8080/api/v1/orders/999
```

**HTTP Status**: `404 Not Found`  
**Response Body**:
```json
{
  "timestamp": "2024-03-20T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Order với ID 999 không tồn tại trên hệ thống!"
}
```

### 2. Chạy Test tự động
Dự án đã tích hợp đầy đủ MockMvc và Mockito test cho Controller và Service:
```powershell
.\gradlew test
```
