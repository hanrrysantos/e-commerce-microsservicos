package br.com.hanrry.user_service.handler;

import br.com.hanrry.user_service.exception.EmailAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<StandardError>handlerEmailAlreadyExistsException(EmailAlreadyExistsException e, HttpServletRequest request){
        String error = "EmailAlreadyExistsException";
        HttpStatus status = HttpStatus.CONFLICT;
        Instant instant = Instant.now();
        StandardError err = new StandardError(instant, status.value(), error,e.getMessage(),request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
