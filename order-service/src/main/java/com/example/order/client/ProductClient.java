package com.example.order.client;

import com.example.order.dto.ProductResponseDTO;
import com.example.order.exception.ProductServiceException;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public ProductClient(
            RestTemplate restTemplate,
            DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public ProductResponseDTO findById(Long productId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("PRODUCT-SERVICE");
        if (instances.isEmpty()) {
            throw new ProductServiceException(
                    "PRODUCT-SERVICE hiện không khả dụng (không tìm thấy instance)",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }

        try {
            ProductResponseDTO product = restTemplate.getForObject(
                    "http://PRODUCT-SERVICE/api/v1/products/{id}",
                    ProductResponseDTO.class,
                    productId);
            if (product == null || product.price() == null) {
                throw new ProductServiceException("Product Service trả về dữ liệu không hợp lệ");
            }
            return product;
        } catch (RestClientException exception) {
            throw new ProductServiceException(
                    "Không thể lấy giá sản phẩm từ Product Service", exception);
        }
    }
}
