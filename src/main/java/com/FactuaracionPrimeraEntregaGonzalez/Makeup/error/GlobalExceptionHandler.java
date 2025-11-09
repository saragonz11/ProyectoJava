package com.FactuaracionPrimeraEntregaGonzalez.Makeup.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorPayload> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req){
        Map<String,String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        var body = new ErrorPayload(false, "Error de validación", req.getRequestURI(), errors, Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorPayload> badRequest(RuntimeException ex, HttpServletRequest req){
        var body = new ErrorPayload(false, ex.getMessage(), req.getRequestURI(), null, Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<ErrorPayload> conflict(RuntimeException ex, HttpServletRequest req){
        var body = new ErrorPayload(false, ex.getMessage(), req.getRequestURI(), null, Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ErrorPayload> notFound(RuntimeException ex, HttpServletRequest req){
        var body = new ErrorPayload(false, ex.getMessage(), req.getRequestURI(), null, Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> generic(Exception ex, HttpServletRequest req){
        var body = new ErrorPayload(false, "Error interno", req.getRequestURI(), null, Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
