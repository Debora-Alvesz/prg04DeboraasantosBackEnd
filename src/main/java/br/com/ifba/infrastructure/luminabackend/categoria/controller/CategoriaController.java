package br.com.ifba.infrastructure.luminabackend.categoria.controller;

import br.com.ifba.infrastructure.luminabackend.categoria.dto.CategoriaGetResponseDto;
import br.com.ifba.infrastructure.luminabackend.categoria.dto.CategoriaPostRequestDto;
import br.com.ifba.infrastructure.luminabackend.categoria.entity.Categoria;
import br.com.ifba.infrastructure.luminabackend.categoria.service.CategoriaService;
import br.com.ifba.infrastructure.luminabackend.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Define a rota base para categorias
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService; // Conecta com as regras de negócio

    @Autowired
    private ObjectMapperUtil objectMapperUtil; // Injeta o utilitário que faz a conversão de objetos (DTOs)

    // C - Criar uma nova categoria (Recebe RequestDto e devolve GetResponseDto)
    @PostMapping
    public ResponseEntity<CategoriaGetResponseDto> criar(@Valid @RequestBody CategoriaPostRequestDto dto) {
        // 1. Converte o DTO recebido do Postman para a Entidade Categoria
        Categoria categoriaEntity = objectMapperUtil.map(dto, Categoria.class);

        // 2. Salva no banco usando o Service
        Categoria categoriaSalva = categoriaService.criar(categoriaEntity);

        // 3. Converte o resultado de volta para o DTO de resposta (escondendo dados como ID)
        CategoriaGetResponseDto responseDto = objectMapperUtil.map(categoriaSalva, CategoriaGetResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // R - Listar todas as categorias (Com paginação)
    @GetMapping
    public ResponseEntity<Page<CategoriaGetResponseDto>> listarTodas(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        // 1. Busca a página de entidades do Service
        Page<Categoria> categorias = categoriaService.listarTodas(pageable);

        // 2. Converte a página de Categoria para uma página de CategoriaGetResponseDto
        Page<CategoriaGetResponseDto> responsePage = categorias.map(
                categoriaEntity -> objectMapperUtil.map(categoriaEntity, CategoriaGetResponseDto.class)
        );

        return ResponseEntity.ok(responsePage);
    }

    // U - Editar uma categoria por ID (Recebe RequestDto e devolve GetResponseDto)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGetResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaPostRequestDto dto) {
        // 1. Converte os novos dados do DTO para a Entidade Categoria
        Categoria dadosAtualizados = objectMapperUtil.map(dto, Categoria.class);

        // 2. Executa a atualização no service e converte o resultado caso ele exista
        return categoriaService.atualizar(id, dadosAtualizados)
                .map(categoriaEntity -> {
                    CategoriaGetResponseDto responseDto = objectMapperUtil.map(categoriaEntity, CategoriaGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 se não achar a categoria
    }

    // D - Deletar uma categoria por ID (Não usa DTO pois o retorno é vazio 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (categoriaService.deletar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 Sucesso
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
    }
}