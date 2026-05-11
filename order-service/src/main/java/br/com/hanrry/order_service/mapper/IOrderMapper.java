package br.com.hanrry.order_service.mapper;

import br.com.hanrry.order_service.database.model.OrderEntity;
import br.com.hanrry.order_service.dto.event.OrderEventDTO;
import br.com.hanrry.order_service.dto.request.OrderRequestDTO;
import br.com.hanrry.order_service.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderMapper {

    OrderResponseDTO toDTO(OrderEntity entity);

    OrderEntity toEntity(OrderRequestDTO requestDTO);

    OrderEventDTO toEventDTO(OrderEntity entity);

    List<OrderResponseDTO> toDTOList(List<OrderEntity> orders);
}
