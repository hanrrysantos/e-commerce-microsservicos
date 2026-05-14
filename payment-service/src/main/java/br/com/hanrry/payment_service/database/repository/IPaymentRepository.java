package br.com.hanrry.payment_service.database.repository;

import br.com.hanrry.payment_service.database.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    boolean existsByOrderId(UUID orderId);

    Optional<PaymentEntity> findByOrderId(UUID orderId);

    Optional<PaymentEntity> findByExternalId(String externalId);
}