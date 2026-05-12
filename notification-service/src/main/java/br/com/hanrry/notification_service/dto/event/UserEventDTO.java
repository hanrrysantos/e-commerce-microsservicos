package br.com.hanrry.notification_service.dto.event;

import br.com.hanrry.notification_service.enums.UserStatus;

public record UserEventDTO (
        String name,
        String email,
        UserStatus userStatus
){
}
