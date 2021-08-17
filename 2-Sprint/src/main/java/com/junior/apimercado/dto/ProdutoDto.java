package com.junior.apimercado.dto;

import lombok.Data;

@Data
public class ProdutoDto {
    private Integer id;
    private String nome;
    private Double price;
    private String description;
    private String date;
    private Integer estoque;
}
