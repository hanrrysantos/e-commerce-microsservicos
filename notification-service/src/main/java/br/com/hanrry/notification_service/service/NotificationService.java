package br.com.hanrry.notification_service.service;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.database.repository.INotificationRepository;
import br.com.hanrry.notification_service.dto.OrderEventDTO;
import br.com.hanrry.notification_service.enums.NotificationStatus;
import br.com.hanrry.notification_service.mapper.INotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final INotificationRepository notificationRepository;
    private final JavaMailSender emailSender;
    private final INotificationMapper notificationMapper; // Este cara monta o texto!

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void processNotification(OrderEventDTO eventDTO) {
        NotificationEntity entity = notificationMapper.toEntity(eventDTO);
        entity.setEmailFrom(emailFrom);
        entity.setSubject("Pedido Recebido: " + eventDTO.orderCode());
        entity.setContent(String.format(
                "Olá %s!\n\nRecebemos o seu pedido %s com sucesso.\nValor total: R$ %s\n\nEstamos processando tudo!",
                eventDTO.clientName(), eventDTO.orderCode(), eventDTO.totalValue()
        ));

        entity = notificationRepository.save(entity);

        try {
            log.info("Tentando enviar e-mail para: {}", entity.getClientEmail());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(entity.getEmailFrom());
            message.setTo(entity.getClientEmail());
            message.setSubject(entity.getSubject());
            message.setText(entity.getContent());

            emailSender.send(message);
            entity.setStatus(NotificationStatus.SENT);
            log.info("E-mail enviado com sucesso!");
        } catch (MailException e) {
            entity.setStatus(NotificationStatus.FAILED);
            log.error("Erro ao enviar e-mail para {}: {}", entity.getClientEmail(), e.getMessage());
        } finally {
            notificationRepository.save(entity);
        }
    }

}
