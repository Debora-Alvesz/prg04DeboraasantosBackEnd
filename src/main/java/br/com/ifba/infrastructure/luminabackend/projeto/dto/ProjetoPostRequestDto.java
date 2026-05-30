package br.com.ifba.infrastructure.luminabackend.projeto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjetoPostRequestDto {

    // Nome do projeto, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo nome é de preenchimento obrigatório.")
    private String nome;

    // Cor de identificação do projeto, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo cor é de preenchimento obrigatório.")
    private String cor;

    // Descrição do projeto, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo descrição é de preenchimento obrigatório.")
    private String descricao;
}