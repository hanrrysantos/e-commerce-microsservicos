package br.com.hanrry.cart_service.controller;

import br.com.hanrry.cart_service.dto.request.AddCartItemRequestDTO;
import br.com.hanrry.cart_service.dto.request.CheckoutCartRequestDTO;
import br.com.hanrry.cart_service.dto.request.UpdateCartItemRequestDTO;
import br.com.hanrry.cart_service.dto.response.CartResponseDTO;
import br.com.hanrry.cart_service.dto.response.OrderResponseDTO;
import br.com.hanrry.cart_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/me")
    public ResponseEntity<CartResponseDTO> findMyCart(@RequestHeader("X-User-Email") String clientEmail) {
        CartResponseDTO cart = cartService.findActiveCart(clientEmail);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/me/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @RequestHeader("X-User-Email") String clientEmail,
            @RequestBody @Valid AddCartItemRequestDTO request
    ) {
        CartResponseDTO cart = cartService.addItem(clientEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PatchMapping("/me/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @RequestHeader("X-User-Email") String clientEmail,
            @PathVariable UUID productId,
            @RequestBody @Valid UpdateCartItemRequestDTO request
    ) {
        CartResponseDTO cart = cartService.updateItem(clientEmail, productId, request);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/me/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @RequestHeader("X-User-Email") String clientEmail,
            @PathVariable UUID productId
    ) {
        CartResponseDTO cart = cartService.removeItem(clientEmail, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Email") String clientEmail) {
        cartService.clearCart(clientEmail);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/me/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(
            @RequestHeader("X-User-Email") String clientEmail,
            @RequestBody @Valid CheckoutCartRequestDTO request
    ) {
        OrderResponseDTO order = cartService.checkout(clientEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
