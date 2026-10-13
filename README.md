# Eureka service discovery

Project nay gom Eureka Server va ba service dang ky vao Eureka:

- `discovery-server`: port `8761`
- `customer-service`: port `8081`
- `product-service`: port `8082`
- `order-service`: port `8083`

Khoi dong Eureka Server:

```bash
./gradlew bootRun
```

Khoi dong tung service trong cac thu muc tuong ung:

```bash
cd customer-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
```

Dashboard Eureka: `http://localhost:8761`

Co the chay hai instance Product Service:

```bash
cd product-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8084"
```

Order Service goi `http://PRODUCT-SERVICE/...` thong qua `@LoadBalanced`
RestTemplate, nen request duoc phan phoi giua cac instance dang ky tren Eureka.
