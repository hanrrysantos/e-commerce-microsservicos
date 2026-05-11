package br.com.hanrry.deliveries_service.service;

import br.com.hanrry.deliveries_service.database.model.DeliveriesEntity;
import br.com.hanrry.deliveries_service.database.repository.IDeliveriesRepository;
import br.com.hanrry.deliveries_service.dto.OrderEventDTO;
import br.com.hanrry.deliveries_service.mapper.IDeliveriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
}
