package br.com.hanrry.deliveries_service.mapper;

import br.com.hanrry.deliveries_service.database.model.DeliveriesEntity;
import br.com.hanrry.deliveries_service.dto.event.OrderEventDTO;
import br.com.hanrry.deliveries_service.dto.response.DeliveriesResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDeliveriesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "address.country", target = "country")
    DeliveriesEntity toEntity(OrderEventDTO eventDTO);

    DeliveriesResponseDTO toDTO(DeliveriesEntity delivery);

    List<DeliveriesResponseDTO> toDTOList(List<DeliveriesEntity> deliveries);
}
