package br.com.ifba.infrastructure.luminabackend.projeto.dto;

import lombok.Data;

@Data
public class ProjetoGetResponseDto {
    private String nome;
    private String cor;
    private String descricao;
}