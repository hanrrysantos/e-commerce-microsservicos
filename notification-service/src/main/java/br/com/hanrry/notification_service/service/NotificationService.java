package br.com.hanrry.notification_service.service;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.database.repository.INotificationRepository;
import br.com.hanrry.notification_service.dto.event.OrderEventDTO;
import br.com.hanrry.notification_service.dto.event.PaymentEventDTO;
import br.com.hanrry.notification_service.dto.event.UserEventDTO;
import br.com.hanrry.notification_service.enums.NotificationStatus;
import br.com.hanrry.notification_service.enums.OrderStatus;
import br.com.hanrry.notification_service.enums.PaymentStatus;
import br.com.hanrry.notification_service.exception.NotNecessaryResendEmailException;
import br.com.hanrry.notification_service.exception.NotificationNotFoundException;
import br.com.hanrry.notification_service.infra.EmailMailer;
import br.com.hanrry.notification_service.mapper.INotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final INotificationRepository notificationRepository;
    private final INotificationMapper notificationMapper;
    private final EmailMailer emailMailer; // Novo componente

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void processOrderNotification(OrderEventDTO eventDTO) {
        if (eventDTO.status() != OrderStatus.PENDING) {
            log.info("Evento de order {} ignorado, status não é PENDING", eventDTO.status());
            return;
        }

        NotificationEntity entity = notificationMapper.OrderEventToEntity(eventDTO);
        entity.setEmailFrom(emailFrom);
        entity.setSubject("Pedido Recebido: " + eventDTO.orderCode());
        entity.setContent(String.format(
            "Olá %s!\n\nRecebemos o seu pedido %s com sucesso.\nValor total: R$ %s\n\nEstamos processando tudo!",
            eventDTO.clientName(), eventDTO.orderCode(), eventDTO.totalValue()
        ));

        executeSending(entity);
    }

    @Transactional
    public void processUserNotification(UserEventDTO eventDTO) {
        NotificationEntity entity = notificationMapper.UserEventToEntity(eventDTO);
        entity.setEmailFrom(emailFrom);
        entity.setSubject("Bem-vindo(a): " + eventDTO.name());
        entity.setContent(String.format(
            "Olá %s!\n\nSua conta está pronta. Que tal conferir as ofertas de hoje?", eventDTO.name()
        ));

        executeSending(entity);
    }

    @Transactional
    public void processPaymentNotification(PaymentEventDTO eventDTO) {
        if (eventDTO.status() == PaymentStatus.PENDING ||
                eventDTO.status() == PaymentStatus.IN_PROCESS ||
                eventDTO.status() == PaymentStatus.IN_MEDIATION ||
                eventDTO.status() == PaymentStatus.AUTHORIZED) {
            return;
        }

        NotificationEntity entity = notificationMapper.PaymentEventToEntity(eventDTO);
        entity.setEmailFrom(emailFrom);

        if (eventDTO.status() == PaymentStatus.APPROVED) {
            entity.setSubject(String.format("Pagamento Aprovado - Pedido %s", eventDTO.orderId()));
            entity.setContent(String.format(
                    "Olá!\n\nO pagamento do seu pedido %s foi confirmado.\nStatus: %s\n\nObrigado!",
                    eventDTO.orderId(),
                    eventDTO.status()
            ));
        } else {
            entity.setSubject(String.format("Pagamento Não Autorizado - Pedido %s", eventDTO.orderId()));
            entity.setContent(String.format(
                    "Olá,\n\nO pagamento do pedido %s não foi autorizado.\nStatus: %s\n\nPor favor, tente outro cartão ou fale com seu banco.",
                    eventDTO.orderId(),
                    eventDTO.status()
            ));
        }

        executeSending(entity);
    }

    @Transactional
    public void resendNotification(UUID id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        if (notification.getStatus() != NotificationStatus.FAILED) {
            throw new NotNecessaryResendEmailException("Only FAILED notifications can be resent");
        }

        log.info("Reenviando notificação: {}", id);
        executeSending(notification);
    }

    private void executeSending(NotificationEntity entity) {
        entity = notificationRepository.save(entity);

        try {
            log.info("Iniciando envio do email {}", entity.getId());
            emailMailer.send(entity);
            entity.setStatus(NotificationStatus.SENT);
            log.info("Email {} enviado com sucesso", entity.getId());
        } catch (MailException e) {
            entity.setStatus(NotificationStatus.FAILED);
            log.error("Falha no envio do email: {}", e.getMessage());
        }
    }


}
