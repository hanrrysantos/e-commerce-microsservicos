package br.com.hanrry.order_service.mapper;

import br.com.hanrry.order_service.database.model.OrderAddressEntity;
import br.com.hanrry.order_service.database.model.OrderEntity;
import br.com.hanrry.order_service.database.model.OrderItemEntity;
import br.com.hanrry.order_service.dto.event.OrderEventDTO;
import br.com.hanrry.order_service.dto.request.OrderAddressRequestDTO;
import br.com.hanrry.order_service.dto.request.OrderItemRequestDTO;
import br.com.hanrry.order_service.dto.request.OrderRequestDTO;
import br.com.hanrry.order_service.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalValue", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderEntity toEntity(OrderRequestDTO requestDTO);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "productName", ignore = true)
    OrderItemEntity toItemEntity(OrderItemRequestDTO requestDTO);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderAddressEntity toAddressEntity(OrderAddressRequestDTO requestDTO);

    OrderResponseDTO toDTO(OrderEntity entity);

    OrderEventDTO toEventDTO(OrderEntity entity);

    List<OrderResponseDTO> toDTOList(List<OrderEntity> orders);
}