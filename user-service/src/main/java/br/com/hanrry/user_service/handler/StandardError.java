package br.com.hanrry.user_service.handler;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record StandardError(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        Instant instant,
        Integer status,
        String error,
        String message,
        String path
){
}
