package com.junior.apimercado.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Produto {
    private Integer id;
    @NotBlank
    private String nome;
    @DecimalMin(value = "0.99")
    private Double price;
    @NotBlank
    private String description;
    @NotNull
    @Past(message = "Formato de data invalido")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    @DecimalMin(value = "0")
    private Integer estoque;

}
