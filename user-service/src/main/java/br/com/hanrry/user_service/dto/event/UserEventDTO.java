package br.com.hanrry.user_service.dto.event;

import br.com.hanrry.user_service.enums.UserStatus;

public record UserEventDTO (
        String name,
        String email,
        UserStatus status
){
}
