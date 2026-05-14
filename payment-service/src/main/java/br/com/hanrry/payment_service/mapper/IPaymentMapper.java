package br.com.hanrry.payment_service.mapper;

import br.com.hanrry.payment_service.database.model.PaymentEntity;
import br.com.hanrry.payment_service.dto.event.OrderEventDTO;
import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import br.com.hanrry.payment_service.dto.request.PaymentRequestDTO;
import br.com.hanrry.payment_service.dto.response.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPaymentMapper {

    PaymentResponseDTO toDTO(PaymentEntity entity);

    PaymentEntity toEntity(PaymentRequestDTO request);


    PaymentEventDTO toEventDTO(PaymentEntity  entity);

    List<PaymentResponseDTO> toDTOList(List<PaymentEntity> payments);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "totalValue", target = "amount")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentEntity eventDTOToEntity(OrderEventDTO event);
}
