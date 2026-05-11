package br.com.hanrry.notification_service.database.repository;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface INotificationRepository extends JpaRepository<NotificationEntity, UUID> {
}
