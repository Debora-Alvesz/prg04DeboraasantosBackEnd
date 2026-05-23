package br.com.ifba.infrastructure.luminabackend.usuario.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    // Gerador automático de ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do usuário
    @Column(nullable = false)
    private String nome;

    // Email usado para login
    @Column(nullable = false, unique = true)
    private String email;

    // Senha em texto simples para avaliação do professor
    @Column(nullable = false)
    private String senha;

    // Papel do usuário (ex: ADMIN, USER)
    private String role;

    // Link ou nome do arquivo da foto de perfil
    private String foto;
}