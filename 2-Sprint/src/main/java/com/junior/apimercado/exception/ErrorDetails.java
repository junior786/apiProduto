package com.junior.apimercado.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetails {
    private String message;
    private LocalDateTime time;
    private int codStatus;
    private String detail;
}
