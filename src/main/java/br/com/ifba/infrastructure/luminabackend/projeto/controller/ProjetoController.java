package br.com.ifba.infrastructure.luminabackend.projeto.controller;

import br.com.ifba.infrastructure.luminabackend.projeto.entity.Projeto;
import br.com.ifba.infrastructure.luminabackend.projeto.service.ProjetoService;
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

    // C - Criar um novo projeto (Retorna 201 Created)
    @PostMapping
    public ResponseEntity<Projeto> criar(@RequestBody Projeto novoProjeto) {
        Projeto projetoSalvo = projetoService.criar(novoProjeto);
        return new ResponseEntity<>(projetoSalvo, HttpStatus.CREATED);
    }

    // R - Listar todos os projetos (Retorna 200 OK)
    @GetMapping
    public ResponseEntity<List<Projeto>> listarTodos() {
        return ResponseEntity.ok(projetoService.listarTodos());
    }

    // R - Buscar um projeto específico por ID (Retorna 200 OK ou 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // U - Editar dados de um projeto existente (Retorna 200 OK ou 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizar(@PathVariable Long id, @RequestBody Projeto dadosAtualizados) {
        return projetoService.atualizar(id, dadosAtualizados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar um projeto por ID (Retorna 204 No Content ou 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (projetoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}