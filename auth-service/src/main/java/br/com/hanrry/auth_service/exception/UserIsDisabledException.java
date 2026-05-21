package br.com.hanrry.auth_service.exception;

public class UserIsDisabledException extends RuntimeException {
    public UserIsDisabledException(String message) {
        super(message);
    }
}
