package br.com.hanrry.cart_service.database.repository;

import br.com.hanrry.cart_service.database.model.CartEntity;
import br.com.hanrry.cart_service.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICartRepository extends JpaRepository<CartEntity, UUID> {

    Optional<CartEntity> findByClientEmailAndStatus(String clientEmail, CartStatus status);
}
