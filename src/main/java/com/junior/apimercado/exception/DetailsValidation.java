package com.junior.apimercado.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class DetailsValidation {
    private String message;
    private LocalDateTime time;
    private int codStatus;
    private String detail;
    private Map errors;

}
