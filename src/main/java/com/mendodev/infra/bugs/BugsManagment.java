package com.mendodev.infra.bugs;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BugsManagment {

    // Custom exception for handling term errors
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CustomNotFoundException extends RuntimeException {
        public CustomNotFoundException(String message) {
            super(message);
        }
    }

    // Exception for handling nonexistent or deleted posts
    @ExceptionHandler({NullPointerException.class, EntityNotFoundException.class})
    public ResponseEntity errorNull(){
        String errorMessage = "The post does not exist or was removed";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(errorMessage));
    }

    // Exception handler for validation errores AND custom not found exception format
    @ExceptionHandler({MethodArgumentNotValidException.class, BugsManagment.CustomNotFoundException.class})
    public ResponseEntity<ErrorMessage> validException(Exception ex) {
        String errorMessage;

        if (ex instanceof MethodArgumentNotValidException) {
            errorMessage = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage();
        } else {
            errorMessage = ex.getMessage();
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(errorMessage));
    }

    // Part of Valid Exception handler
    private static class ErrorMessage {
        private String error;
        public ErrorMessage(String error) {
            this.error = error;
        }
        public String getError() {
            return error;
        }
    }
}
