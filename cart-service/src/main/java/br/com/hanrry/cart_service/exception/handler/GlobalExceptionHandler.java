package br.com.hanrry.cart_service.exception.handler;

import br.com.hanrry.cart_service.exception.CartEmptyException;
import br.com.hanrry.cart_service.exception.CartItemNotFoundException;
import br.com.hanrry.cart_service.exception.InsufficientStockException;
import br.com.hanrry.cart_service.exception.InvalidUserContextException;
import br.com.hanrry.cart_service.exception.ProductNotAvailableException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserContextException.class)
    public ResponseEntity<StandardError> handleInvalidUserContext(InvalidUserContextException e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.UNAUTHORIZED, "InvalidUserContextException");
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<StandardError> handleCartItemNotFound(CartItemNotFoundException e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.NOT_FOUND, "CartItemNotFoundException");
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<StandardError> handleCartEmpty(CartEmptyException e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.BAD_REQUEST, "CartEmptyException");
    }

    @ExceptionHandler({ProductNotAvailableException.class, InsufficientStockException.class})
    public ResponseEntity<StandardError> handleProductValidation(RuntimeException e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.CONFLICT, e.getClass().getSimpleName());
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<StandardError> handleFeignNotFound(FeignException.NotFound e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.NOT_FOUND, "ResourceNotFoundException");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        return buildError(e, request, HttpStatus.BAD_REQUEST, "ValidationException");
    }

    private ResponseEntity<StandardError> buildError(Exception e, HttpServletRequest request, HttpStatus status, String error) {
        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }
}
