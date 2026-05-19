package br.com.hanrry.delivery_service.database.repository;

import br.com.hanrry.delivery_service.database.model.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IDeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    Optional<DeliveryEntity> findByTrackingCode(String trackingCode);
}
