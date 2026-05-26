package br.com.hanrry.cart_service.exception.handler;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
