package br.com.hanrry.notification_service.service;

import br.com.hanrry.notification_service.database.model.NotificationEntity;
import br.com.hanrry.notification_service.database.repository.INotificationRepository;
import br.com.hanrry.notification_service.dto.response.NotificationResponseDTO;
import br.com.hanrry.notification_service.exception.NotificationNotFoundException;
import br.com.hanrry.notification_service.mapper.INotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationFindService {

    private final INotificationMapper notificationMapper;
    private final INotificationRepository notificationRepository;

    public NotificationResponseDTO findNotificationById(UUID id) {
        NotificationEntity notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("Notification not found with this id: " + id)
        );

        return notificationMapper.toDTO(notification);
    }

    public List<NotificationResponseDTO> findAllNotifications(){
        List<NotificationEntity> notifications = notificationRepository.findAll();

        return notificationMapper.toDTOList(notifications);
    }

    public List<NotificationResponseDTO> findNotificationsByClientEmail(String email){
        List<NotificationEntity> notifications = notificationRepository.findByClientEmail(email);

        return notificationMapper.toDTOList(notifications);
    }
}
