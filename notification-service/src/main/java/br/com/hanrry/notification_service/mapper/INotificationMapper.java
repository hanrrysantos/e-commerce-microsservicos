package br.com.hanrry.notification_service.mapper;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.dto.event.OrderEventDTO;
import br.com.hanrry.notification_service.dto.event.UserEventDTO;
import br.com.hanrry.notification_service.dto.response.NotificationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface INotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "name", source = "clientName")
    NotificationEntity OrderEventToEntity(OrderEventDTO event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "clientEmail", source = "email")
    NotificationEntity UserEventToEntity(UserEventDTO event);


    NotificationResponseDTO toDTO(NotificationEntity entity);


    List<NotificationResponseDTO> toDTOList(List<NotificationEntity> notifications);

}
