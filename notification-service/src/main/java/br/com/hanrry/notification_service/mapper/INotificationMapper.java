package br.com.hanrry.notification_service.mapper;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.dto.event.OrderEventDTO;
import br.com.hanrry.notification_service.dto.event.PaymentEventDTO;
import br.com.hanrry.notification_service.dto.event.UserEventDTO;
import br.com.hanrry.notification_service.dto.response.NotificationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface INotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "emailFrom", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "name", source = "clientName")
    NotificationEntity OrderEventToEntity(OrderEventDTO event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "clientEmail", source = "email")
    NotificationEntity UserEventToEntity(UserEventDTO event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "emailFrom", ignore = true)
    @Mapping(target = "name", source = "clientName")
    NotificationEntity PaymentEventToEntity(PaymentEventDTO eventDTO);

    NotificationResponseDTO toDTO(NotificationEntity entity);


    List<NotificationResponseDTO> toDTOList(List<NotificationEntity> notifications);

}
