package br.com.hanrry.delivery_service.mapper;

import br.com.hanrry.delivery_service.database.model.DeliveryEntity;
import br.com.hanrry.delivery_service.dto.event.OrderEventDTO;
import br.com.hanrry.delivery_service.dto.response.DeliveryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDeliveryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "address.country", target = "country")
    DeliveryEntity toEntity(OrderEventDTO eventDTO);

    DeliveryResponseDTO toDTO(DeliveryEntity delivery);

    List<DeliveryResponseDTO> toDTOList(List<DeliveryEntity> deliveries);
}
