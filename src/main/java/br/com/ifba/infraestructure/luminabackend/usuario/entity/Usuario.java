package br.com.ifba.infraestructure.luminabackend.usuario.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Torna o e-mail obrigatório e único
    private String email;

    @Column(nullable = false)
    private String senha;

    private String nome;
}