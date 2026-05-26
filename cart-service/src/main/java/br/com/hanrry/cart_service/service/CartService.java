package br.com.hanrry.cart_service.service;

import br.com.hanrry.cart_service.client.OrderClient;
import br.com.hanrry.cart_service.client.ProductClient;
import br.com.hanrry.cart_service.database.model.CartEntity;
import br.com.hanrry.cart_service.database.model.CartItemEntity;
import br.com.hanrry.cart_service.database.repository.ICartRepository;
import br.com.hanrry.cart_service.dto.request.AddCartItemRequestDTO;
import br.com.hanrry.cart_service.dto.request.CheckoutCartRequestDTO;
import br.com.hanrry.cart_service.dto.request.OrderItemRequestDTO;
import br.com.hanrry.cart_service.dto.request.OrderRequestDTO;
import br.com.hanrry.cart_service.dto.request.UpdateCartItemRequestDTO;
import br.com.hanrry.cart_service.dto.response.CartResponseDTO;
import br.com.hanrry.cart_service.dto.response.OrderResponseDTO;
import br.com.hanrry.cart_service.dto.response.ProductClientResponseDTO;
import br.com.hanrry.cart_service.enums.CartStatus;
import br.com.hanrry.cart_service.enums.ProductStatus;
import br.com.hanrry.cart_service.exception.CartEmptyException;
import br.com.hanrry.cart_service.exception.CartItemNotFoundException;
import br.com.hanrry.cart_service.exception.InsufficientStockException;
import br.com.hanrry.cart_service.exception.InvalidUserContextException;
import br.com.hanrry.cart_service.exception.ProductNotAvailableException;
import br.com.hanrry.cart_service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ICartRepository cartRepository;
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final CartMapper cartMapper;

    @Transactional
    public CartResponseDTO findActiveCart(String clientEmail) {
        CartEntity cart = findOrCreateActiveCart(normalizeEmail(clientEmail));
        return cartMapper.toDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItem(String clientEmail, AddCartItemRequestDTO request) {
        String email = normalizeEmail(clientEmail);
        CartEntity cart = findOrCreateActiveCart(email);
        CartItemEntity existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst()
                .orElse(null);
        Integer desiredQuantity = existingItem == null
                ? request.quantity()
                : existingItem.getQuantity() + request.quantity();
        ProductClientResponseDTO product = validateProduct(request.productId(), desiredQuantity);

        if (existingItem == null) {
            cart.getItems().add(createItem(cart, request.quantity(), product));
        } else {
            updateExistingItem(existingItem, desiredQuantity, product);
        }

        recalculateTotal(cart);
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional
    public CartResponseDTO updateItem(String clientEmail, UUID productId, UpdateCartItemRequestDTO request) {
        CartEntity cart = findOrCreateActiveCart(normalizeEmail(clientEmail));
        ProductClientResponseDTO product = validateProduct(productId, request.quantity());
        CartItemEntity item = findItem(cart, productId);

        updateExistingItem(item, request.quantity(), product);
        recalculateTotal(cart);

        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional
    public CartResponseDTO removeItem(String clientEmail, UUID productId) {
        CartEntity cart = findOrCreateActiveCart(normalizeEmail(clientEmail));
        CartItemEntity item = findItem(cart, productId);

        cart.getItems().remove(item);
        recalculateTotal(cart);

        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional
    public void clearCart(String clientEmail) {
        CartEntity cart = findOrCreateActiveCart(normalizeEmail(clientEmail));
        cart.getItems().clear();
        cart.setStatus(CartStatus.CLEARED);
        recalculateTotal(cart);
        cartRepository.save(cart);
    }

    @Transactional
    public OrderResponseDTO checkout(String clientEmail, CheckoutCartRequestDTO request) {
        String email = normalizeEmail(clientEmail);
        CartEntity cart = findOrCreateActiveCart(email);

        if (cart.getItems().isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        cart.getItems().forEach(item -> validateProduct(item.getProductId(), item.getQuantity()));

        OrderRequestDTO orderRequest = new OrderRequestDTO(
                request.clientName(),
                email,
                cart.getItems().stream()
                        .map(item -> new OrderItemRequestDTO(item.getProductId(), item.getQuantity()))
                        .toList(),
                request.address()
        );

        OrderResponseDTO order = orderClient.createOrder(orderRequest);
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return order;
    }

    private CartEntity findOrCreateActiveCart(String clientEmail) {
        return cartRepository.findByClientEmailAndStatus(clientEmail, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setClientEmail(clientEmail);
                    return cartRepository.save(cart);
                });
    }

    private ProductClientResponseDTO validateProduct(UUID productId, Integer quantity) {
        ProductClientResponseDTO product = productClient.findById(productId);

        if (product.productStatus() != ProductStatus.ACTIVE) {
            throw new ProductNotAvailableException("Product is not available: " + product.name());
        }

        if (product.stockQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.name());
        }

        return product;
    }

    private CartItemEntity createItem(CartEntity cart, Integer quantity, ProductClientResponseDTO product) {
        CartItemEntity item = new CartItemEntity();
        item.setCart(cart);
        item.setProductId(product.id());
        updateExistingItem(item, quantity, product);
        return item;
    }

    private void updateExistingItem(CartItemEntity item, Integer quantity, ProductClientResponseDTO product) {
        item.setProductName(product.name());
        item.setUnitPrice(product.price());
        item.setQuantity(quantity);
        item.setSubtotal(product.price().multiply(BigDecimal.valueOf(quantity)));
    }

    private CartItemEntity findItem(CartEntity cart, UUID productId) {
        return cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with product id: " + productId));
    }

    private void recalculateTotal(CartEntity cart) {
        BigDecimal totalValue = cart.getItems().stream()
                .map(CartItemEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalValue(totalValue);
    }

    private String normalizeEmail(String clientEmail) {
        if (!StringUtils.hasText(clientEmail)) {
            throw new InvalidUserContextException("Authenticated user email was not provided");
        }

        return clientEmail.trim().toLowerCase();
    }
}
