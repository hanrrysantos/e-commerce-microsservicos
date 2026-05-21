package br.com.hanrry.auth_service.dto.response;

public record AuthResponseDTO (
        String token,
        String email,
        String role
){
}
