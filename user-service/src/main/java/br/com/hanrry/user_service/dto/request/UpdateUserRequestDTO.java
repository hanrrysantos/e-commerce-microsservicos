package br.com.hanrry.user_service.dto.request;

public record UpdateUserRequestDTO (
        String name,
        String email,
        String password
){
}
