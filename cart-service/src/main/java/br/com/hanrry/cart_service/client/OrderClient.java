package br.com.hanrry.cart_service.client;

import br.com.hanrry.cart_service.dto.request.OrderRequestDTO;
import br.com.hanrry.cart_service.dto.response.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderClient {

    @PostMapping("/api/orders")
    OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request);
}
