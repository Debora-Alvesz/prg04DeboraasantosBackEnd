package br.com.ifba.infrastructure.luminabackend.usuario.dto;

import lombok.Data;

@Data
public class UsuarioPostRequestDto {
    private String nome;
    private String email;
    private String senha;
    private String role;
}