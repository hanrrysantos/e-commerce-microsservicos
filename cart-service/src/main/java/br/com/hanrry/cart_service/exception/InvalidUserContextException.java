package br.com.hanrry.cart_service.exception;

public class InvalidUserContextException extends RuntimeException {

    public InvalidUserContextException(String message) {
        super(message);
    }
}
