package br.com.ifba.infrastructure.luminabackend.usuario.dto;

import lombok.Data;

@Data
public class UsuarioGetResponseDto {
    private String nome;
    private String email;
    private String role;
}