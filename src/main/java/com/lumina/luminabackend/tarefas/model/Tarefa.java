package com.lumina.luminabackend.tarefas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Diz ao Spring que isso é uma tabela do banco de dados
@Table(name = "tarefas") // Define o nome da tabela
@Data // O Lombok gera automaticamente todos os Getters, Setters e construtores
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai gerar o ID automaticamente
    private Long id;

    @Column(nullable = false) // Torna o título obrigatório no banco
    private String titulo;

    @Column(length = 500) // Define um limite de caracteres para a descrição
    private String descricao;

    private String status; 
}