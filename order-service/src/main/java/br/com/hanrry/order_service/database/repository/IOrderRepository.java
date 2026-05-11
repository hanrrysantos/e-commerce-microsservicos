package br.com.hanrry.order_service.database.repository;

import br.com.hanrry.order_service.database.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByClientEmail(String email);

}
