package br.com.hanrry.auth_service.dto.response;

import java.util.UUID;

public record UserClientResponseDTO(
        UUID id,
        String name,
        String email,
        String password,
        String role,
        String status
) {
}
