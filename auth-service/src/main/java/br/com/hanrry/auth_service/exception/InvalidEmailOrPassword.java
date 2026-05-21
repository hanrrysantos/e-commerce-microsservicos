package br.com.hanrry.auth_service.exception;

public class InvalidEmailOrPassword extends RuntimeException {
    public InvalidEmailOrPassword(String message) {
        super(message);
    }
}
