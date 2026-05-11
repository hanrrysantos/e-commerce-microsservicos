package br.com.hanrry.deliveries_service.service;

import br.com.hanrry.deliveries_service.database.model.DeliveriesEntity;
import br.com.hanrry.deliveries_service.database.repository.IDeliveriesRepository;
import br.com.hanrry.deliveries_service.dto.event.OrderEventDTO;
import br.com.hanrry.deliveries_service.dto.response.DeliveriesResponseDTO;
import br.com.hanrry.deliveries_service.enums.DeliveriesStatus;
import br.com.hanrry.deliveries_service.exception.DeliveryNotFoundException;
import br.com.hanrry.deliveries_service.mapper.IDeliveriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveriesService {

    private final IDeliveriesRepository deliveriesRepository;
    private final IDeliveriesMapper deliveriesMapper;

    @Transactional
    public void processOrder(OrderEventDTO event){

        DeliveriesEntity entity = deliveriesMapper.toEntity(event);

        deliveriesRepository.save(entity);
    }

    public DeliveriesResponseDTO findDeliveryById(UUID id){
        DeliveriesEntity delivery = deliveriesRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException("Delivery not found with this id: " + id)
        );

        return deliveriesMapper.toDTO(delivery);
    }

    public List<DeliveriesResponseDTO> findAllDeliveries(){
        List<DeliveriesEntity> deliveries = deliveriesRepository.findAll();

        return deliveriesMapper.toDTOList(deliveries);
    }

    public DeliveriesResponseDTO findDeliveryByTrackingCode(String trackingCode){
        DeliveriesEntity delivery = deliveriesRepository.findByTrackingCode(trackingCode)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with this tracking code: " + trackingCode));

        return deliveriesMapper.toDTO(delivery);
    }

    public DeliveriesResponseDTO updateDeliveryStatus(UUID id, DeliveriesStatus status){
        DeliveriesEntity delivery = deliveriesRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException("Delivery not found with this id: " + id)
        );

        if (status == DeliveriesStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());
        }

        delivery.setStatus(status);

        DeliveriesEntity savedDelivery = deliveriesRepository.save(delivery);

        return deliveriesMapper.toDTO(savedDelivery);
    }
    
}
