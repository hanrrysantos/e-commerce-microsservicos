package br.com.hanrry.notification_service.exception;

public class NotNecessaryResendEmailException extends RuntimeException {
    public NotNecessaryResendEmailException(String message) {
        super(message);
    }
}
