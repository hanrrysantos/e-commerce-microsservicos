package br.com.hanrry.deliveries_service.database.repository;

import br.com.hanrry.deliveries_service.database.model.DeliveriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDeliveriesRepository extends JpaRepository<DeliveriesEntity, UUID> {
}
