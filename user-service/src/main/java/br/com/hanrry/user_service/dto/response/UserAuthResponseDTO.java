package br.com.hanrry.user_service.dto.response;

import br.com.hanrry.user_service.enums.UserStatus;

import java.util.UUID;

public record UserAuthResponseDTO (
        UUID id,
        String name,
        String email,
        String password,
        UserStatus status,
        String role
){
}
