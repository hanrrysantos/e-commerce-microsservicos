package br.com.hanrry.notification_service.infra;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMailer {

    private final JavaMailSender emailSender;

    public void send(NotificationEntity entity) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(entity.getEmailFrom());
        message.setTo(entity.getClientEmail());
        message.setSubject(entity.getSubject());
        message.setText(entity.getContent());
        emailSender.send(message);
    }
}
