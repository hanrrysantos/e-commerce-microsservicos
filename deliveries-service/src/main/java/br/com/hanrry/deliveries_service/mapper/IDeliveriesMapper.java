package br.com.hanrry.deliveries_service.mapper;

import br.com.hanrry.deliveries_service.database.model.DeliveriesEntity;
import br.com.hanrry.deliveries_service.dto.OrderEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDeliveriesMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "productType", source = "typeProduct")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "trackingCode", ignore = true)
    DeliveriesEntity toEntity(OrderEventDTO eventDTO);
}
