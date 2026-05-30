package br.com.ifba.infrastructure.luminabackend.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioPostRequestDto {

    // Nome do usuário, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo nome é de preenchimento obrigatório.")
    private String nome;

    // E-mail do usuário, campo obrigatório e precisa ter um formato válido
    @NotBlank(message = "O campo e-mail é de preenchimento obrigatório.")
    @Email(message = "O e-mail informado deve possuir um formato válido.")
    private String email;

    // Senha do usuário, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo senha é de preenchimento obrigatório.")
    private String senha;

    // Perfil/Role do usuário, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo perfil é de preenchimento obrigatório.")
    private String role;
}