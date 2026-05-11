package br.com.hanrry.order_service.controller;

import br.com.hanrry.order_service.dto.request.OrderRequestDTO;
import br.com.hanrry.order_service.dto.response.OrderResponseDTO;
import br.com.hanrry.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody
            @Valid OrderRequestDTO requestDTO
    ){
    OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }
}
