package br.com.ifba.infrastructure.luminabackend.tarefas.controller;

import br.com.ifba.infrastructure.luminabackend.tarefas.dto.TarefaGetResponseDto;
import br.com.ifba.infrastructure.luminabackend.tarefas.dto.TarefaPostRequestDto;
import br.com.ifba.infrastructure.luminabackend.tarefas.entity.Tarefa;
import br.com.ifba.infrastructure.luminabackend.tarefas.service.TarefaService;
import br.com.ifba.infrastructure.luminabackend.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/tarefas") // Define a rota padrão exigida no documento do projeto
public class TarefaController {

    @Autowired
    private TarefaService tarefaService; // Conecta com a camada de serviço (regras de negócio)

    @Autowired
    private ObjectMapperUtil objectMapperUtil; // Injeta o utilitário de conversão (DTOs)

    // C - Criar uma nova tarefa (Recebe RequestDto e devolve GetResponseDto)
    @PostMapping
    public ResponseEntity<TarefaGetResponseDto> criar( @Valid @RequestBody TarefaPostRequestDto dto) {
        // 1. Converte o DTO recebido para a Entidade Tarefa antes de salvar
        Tarefa tarefaEntity = objectMapperUtil.map(dto, Tarefa.class);

        // 2. Salva no banco de dados usando o Service
        Tarefa tarefaSalva = tarefaService.criar(tarefaEntity);

        // 3. Converte a entidade salva de volta para o DTO de resposta (escondendo o ID)
        TarefaGetResponseDto responseDto = objectMapperUtil.map(tarefaSalva, TarefaGetResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // R - Listar todas as tarefas cadastradas (Com paginação)
    @GetMapping
    public ResponseEntity<Page<TarefaGetResponseDto>> listarTodas(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        // 1. Busca a página de entidades do Service
        Page<Tarefa> tarefas = tarefaService.listarTodas(pageable);

        // 2. Converte a página de Entidade (Tarefa) para DTO (TarefaGetResponseDto)
        Page<TarefaGetResponseDto> responsePage = tarefas.map(
                tarefa -> objectMapperUtil.map(tarefa, TarefaGetResponseDto.class)
        );

        return ResponseEntity.ok(responsePage);
    }

    // R - Buscar uma única tarefa por ID (Devolve GetResponseDto se encontrar)
    @GetMapping("/{id}")
    public ResponseEntity<TarefaGetResponseDto> buscarPorId(@PathVariable Long id) {
        // Busca no service e, se existir, faz a conversão para DTO antes de mandar a resposta
        return tarefaService.buscarPorId(id)
                .map(tarefaEntity -> {
                    TarefaGetResponseDto responseDto = objectMapperUtil.map(tarefaEntity, TarefaGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 caso não exista
    }

    // U - Atualizar uma tarefa existente (Recebe RequestDto e devolve GetResponseDto)
    @PutMapping("/{id}")
    public ResponseEntity<TarefaGetResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaPostRequestDto dto) {
        // 1. Converte os dados do DTO para a Entidade Tarefa
        Tarefa dadosAtualizados = objectMapperUtil.map(dto, Tarefa.class);

        // 2. Executa a atualização e converte o resultado caso ele exista no banco
        return tarefaService.atualizar(id, dadosAtualizados)
                .map(tarefaEntity -> {
                    TarefaGetResponseDto responseDto = objectMapperUtil.map(tarefaEntity, TarefaGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar uma tarefa por ID (Não usa DTO pois o retorno é vazio 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (tarefaService.deletar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 Sucesso
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
    }
}