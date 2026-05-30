package br.com.ifba.infrastructure.luminabackend.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaPostRequestDto {
    //Impede o recebimento de valores nulos, vazios ou contendo apenas espaços.
    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;
}