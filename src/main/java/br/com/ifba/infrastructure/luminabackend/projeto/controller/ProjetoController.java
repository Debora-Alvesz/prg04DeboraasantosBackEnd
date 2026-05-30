package br.com.ifba.infrastructure.luminabackend.projeto.controller;

import br.com.ifba.infrastructure.luminabackend.projeto.dto.ProjetoGetResponseDto;
import br.com.ifba.infrastructure.luminabackend.projeto.dto.ProjetoPostRequestDto;
import br.com.ifba.infrastructure.luminabackend.projeto.entity.Projeto;
import br.com.ifba.infrastructure.luminabackend.projeto.service.ProjetoService;
import br.com.ifba.infrastructure.luminabackend.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projeto") // Define a rota base para projetos
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService; // Conecta com as regras de negócio de projeto

    @Autowired
    private ObjectMapperUtil objectMapperUtil; // Injeta o utilitário de conversão (DTOs)

    // C - Criar um novo projeto (Recebe RequestDto e devolve GetResponseDto)
    @PostMapping
    public ResponseEntity<ProjetoGetResponseDto> criar(@Valid @RequestBody ProjetoPostRequestDto dto) {
        // 1. Converte o DTO recebido para a Entidade Projeto antes de salvar
        Projeto projetoEntity = objectMapperUtil.map(dto, Projeto.class);

        // 2. Salva no banco de dados usando o Service
        Projeto projetoSalvo = projetoService.criar(projetoEntity);

        // 3. Converte a entidade salva de volta para o DTO de resposta (escondendo o ID)
        ProjetoGetResponseDto responseDto = objectMapperUtil.map(projetoSalvo, ProjetoGetResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // R - Listar todos os projetos (Converte a lista de entidades para lista de GetResponseDto)
    @GetMapping
    public ResponseEntity<List<ProjetoGetResponseDto>> listarTodos() {
        // 1. Busca a lista de entidades do Service
        List<Projeto> projetos = projetoService.listarTodos();

        // 2. Converte toda a lista de Projeto para uma lista de ProjetoGetResponseDto
        List<ProjetoGetResponseDto> responseList = objectMapperUtil.mapAll(projetos, ProjetoGetResponseDto.class);

        return ResponseEntity.ok(responseList);
    }

    // R - Buscar um projeto específico por ID (Devolve GetResponseDto se encontrar)
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoGetResponseDto> buscarPorId(@PathVariable Long id) {
        // Busca no service e, se existir, faz a conversão para DTO antes de mandar a resposta
        return projetoService.buscarPorId(id)
                .map(projetoEntity -> {
                    ProjetoGetResponseDto responseDto = objectMapperUtil.map(projetoEntity, ProjetoGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 caso não exista
    }

    // U - Editar dados de um projeto existente (Recebe RequestDto e devolve GetResponseDto)
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoGetResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody ProjetoPostRequestDto dto) {
        // 1. Converte os dados do DTO para a Entidade Projeto
        Projeto dadosAtualizados = objectMapperUtil.map(dto, Projeto.class);

        // 2. Executa a atualização e converte o resultado caso ele exista no banco
        return projetoService.atualizar(id, dadosAtualizados)
                .map(projetoEntity -> {
                    ProjetoGetResponseDto responseDto = objectMapperUtil.map(projetoEntity, ProjetoGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar um projeto por ID (Não usa DTO pois o retorno é vazio 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (projetoService.deletar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 Sucesso
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
    }
}