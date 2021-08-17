package com.junior.apimercado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ResponseError {
    @ExceptionHandler(ExceptionNotFound.class)
    public ResponseEntity<?> handlerResourceNotFound(ExceptionNotFound exceptionNotFound) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Erro ao encontrar a pagina, verifique a URL")
                .time(LocalDateTime.now())
                .codStatus(404)
                .detail(exceptionNotFound.getMessage())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidExcepetion(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>(5);
        fieldErrors.forEach(erro -> errors.put(erro.getField(), erro.getDefaultMessage()));

        DetailsValidation detailsValidation = DetailsValidation.builder()
                .errors(errors)
                .detail("Body com erros")
                .codStatus(400)
                .message("Verifique o corpo da requisição")
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(detailsValidation, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> hlandeHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Houve um erro no corpo da requisição")
                .codStatus(400)
                .detail(httpMessageNotReadableException.getCause().getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExcepeptionNameInvalid.class)
    public ResponseEntity<?> handleExcepetionNameInvalid(ExcepeptionNameInvalid excepeptionNameInvalid) {
        Map<String, String> mapError = new HashMap<>(1);
        mapError.put("name", "Já existe um produto com esse nome");
        DetailsValidation detailsValidation = DetailsValidation.builder()
                .message(excepeptionNameInvalid.getMessage())
                .time(LocalDateTime.now())
                .errors(mapError)
                .detail("Verifique o nome")
                .codStatus(400)
                .build();

        return new ResponseEntity<>(detailsValidation, HttpStatus.BAD_REQUEST);

    }
}

