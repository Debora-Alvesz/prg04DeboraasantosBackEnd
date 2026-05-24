package br.com.ifba.infrastructure.luminabackend.tarefas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TarefaGetResponseDto {
    private String titulo;
    private String descricao;
    private String status;
    private String prioridade;
    private LocalDate dataPrazo;
}