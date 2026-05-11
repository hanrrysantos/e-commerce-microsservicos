package br.com.hanrry.order_service.service;

import br.com.hanrry.order_service.database.model.OrderEntity;
import br.com.hanrry.order_service.database.model.OrderItemEntity;
import br.com.hanrry.order_service.database.repository.IOrderRepository;
import br.com.hanrry.order_service.dto.event.OrderEventDTO;
import br.com.hanrry.order_service.dto.request.OrderRequestDTO;
import br.com.hanrry.order_service.dto.response.OrderResponseDTO;
import br.com.hanrry.order_service.enums.OrderStatus;
import br.com.hanrry.order_service.exception.OrderCannotBeCancelledException;
import br.com.hanrry.order_service.exception.OrderNotFoundException;
import br.com.hanrry.order_service.exception.OrdersNotFoundException;
import br.com.hanrry.order_service.mapper.IOrderMapper;
import br.com.hanrry.order_service.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final IOrderMapper orderMapper;
    private final IOrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO){
        OrderEntity order = orderMapper.toEntity(requestDTO);

        order.getItems().forEach(item -> {
            BigDecimal subtotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subtotal);
            item.setOrder(order);
        });

        BigDecimal totalValue = order.getItems().stream()
                .map(OrderItemEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalValue(totalValue);

        OrderEntity savedOrder = orderRepository.save(order);

        OrderEventDTO event = orderMapper.toEventDTO(savedOrder);
        orderProducer.sendOrderEvent(event);

        return orderMapper.toDTO(savedOrder);
    }

    public OrderResponseDTO findOrderById(UUID id){
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with this id: " + id)
        );

        return orderMapper.toDTO(order);
    }

    public List<OrderResponseDTO> findAllOrders(){
        List<OrderEntity> orders = orderRepository.findAll();

        return orderMapper.toDTOList(orders);
    }

    public List<OrderResponseDTO> findOrdersByClientEmail(String email){
        List<OrderEntity> orders = orderRepository.findByClientEmail(email);

        if (orders.isEmpty()) {
            throw new OrdersNotFoundException("Orders not found with this email: " + email);
        }

        return orderMapper.toDTOList(orders);
    }

    public OrderResponseDTO updateOrderStatus(UUID id, OrderStatus status){
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with this id: " + id)
        );

        order.setStatus(status);

        OrderEntity savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    public void cancelOrder(UUID id){
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with this id: " + id)
        );

        if (order.getStatus() == OrderStatus.DELIVERED ||
                order.getStatus() == OrderStatus.IN_TRANSIT) {
            throw new OrderCannotBeCancelledException("Order cannot be cancelled with status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
