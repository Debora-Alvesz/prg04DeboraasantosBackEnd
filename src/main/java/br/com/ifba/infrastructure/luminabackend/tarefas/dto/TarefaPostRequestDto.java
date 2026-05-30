package br.com.ifba.infrastructure.luminabackend.tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TarefaPostRequestDto {

    // Título da tarefa, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo título é de preenchimento obrigatório.")
    private String titulo;

    // Descrição da tarefa, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo descrição é de preenchimento obrigatório.")
    private String descricao;

    // Status da tarefa, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo status é de preenchimento obrigatório.")
    private String status;

    // Prioridade da tarefa, campo obrigatório e não pode ser vazio
    @NotBlank(message = "O campo prioridade é de preenchimento obrigatório.")
    private String prioridade;

    // Data limite para conclusão, campo objeto obrigatório (não pode ser nulo)
    @NotNull(message = "O campo data prazo é de preenchimento obrigatório.")
    private LocalDate dataPrazo;
}