package br.com.hanrry.delivery_service.service;

import br.com.hanrry.delivery_service.database.model.DeliveryEntity;
import br.com.hanrry.delivery_service.database.repository.IDeliveryRepository;
import br.com.hanrry.delivery_service.dto.event.OrderEventDTO;
import br.com.hanrry.delivery_service.dto.response.DeliveryResponseDTO;
import br.com.hanrry.delivery_service.enums.DeliveryStatus;
import br.com.hanrry.delivery_service.exception.DeliveryNotFoundException;
import br.com.hanrry.delivery_service.mapper.IDeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final IDeliveryRepository deliveriesRepository;
    private final IDeliveryMapper deliveriesMapper;

    @Transactional
    public void processOrder(OrderEventDTO event){

        DeliveryEntity entity = deliveriesMapper.toEntity(event);

        deliveriesRepository.save(entity);
    }

    public DeliveryResponseDTO findDeliveryById(UUID id){
        DeliveryEntity delivery = deliveriesRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException("Delivery not found with this id: " + id)
        );

        return deliveriesMapper.toDTO(delivery);
    }

    public List<DeliveryResponseDTO> findAllDeliveries(){
        List<DeliveryEntity> deliveries = deliveriesRepository.findAll();

        return deliveriesMapper.toDTOList(deliveries);
    }

    public DeliveryResponseDTO findDeliveryByTrackingCode(String trackingCode){
        DeliveryEntity delivery = deliveriesRepository.findByTrackingCode(trackingCode)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with this tracking code: " + trackingCode));

        return deliveriesMapper.toDTO(delivery);
    }

    public DeliveryResponseDTO updateDeliveryStatus(UUID id, DeliveryStatus status){
        DeliveryEntity delivery = deliveriesRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException("Delivery not found with this id: " + id)
        );

        if (status == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());
        }

        delivery.setStatus(status);

        DeliveryEntity savedDelivery = deliveriesRepository.save(delivery);

        return deliveriesMapper.toDTO(savedDelivery);
    }
    
}
