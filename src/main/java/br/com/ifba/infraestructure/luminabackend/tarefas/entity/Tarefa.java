package br.com.ifba.infraestructure.luminabackend.tarefas.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity // Diz ao Spring que isso é uma tabela do banco de dados
@Table(name = "tarefas") // Define o nome da tabela
@Data // O Lombok gera automaticamente todos os Getters e Setters
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o ID automaticamente
    private Long id;

    @Column(nullable = false) // Torna o título obrigatório
    private String titulo;

    @Column(length = 500) // Define limite de caracteres para a descrição
    private String descricao;

    private String status; // Status da tarefa (ex: Em Andamento, Concluído)

    private String prioridade; // Prioridade da tarefa (ex: Alta, Média, Baixa)

    private LocalDate dataPrazo; // Guarda a data limite para entrega da tarefa
}