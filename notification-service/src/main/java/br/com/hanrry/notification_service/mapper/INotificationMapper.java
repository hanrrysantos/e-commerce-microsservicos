package br.com.hanrry.notification_service.mapper;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.dto.OrderEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface INotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderId", source = "id")
    NotificationEntity toEntity(OrderEventDTO event);
}
