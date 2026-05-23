package br.com.ifba.infraestructure.luminabackend.projeto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "projetos")
@Data
public class Projeto {

    // Gerador automático de ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do projeto
    @Column(nullable = false)
    private String nome;

    // Cor em código HEX para o Front-end (ex: #FFFFFF)
    private String cor;

    // Descrição do que é o projeto
    private String descricao;
}