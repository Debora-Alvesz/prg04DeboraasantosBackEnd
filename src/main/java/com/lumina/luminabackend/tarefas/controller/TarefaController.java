package com.lumina.luminabackend.tarefas.controller;

import com.lumina.luminabackend.tarefas.model.Tarefa;
import com.lumina.luminabackend.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    // Criar uma nova tarefa (Retorna 201 Created)
    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa novaTarefa) {
        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }

    // Buscar tarefa por ID (Retorna 200 OK ou 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar tarefa existente (Retorna 200 OK ou 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarefaAtualizada.setId(id); // Garante que vai atualizar o ID correto
        Tarefa tarefaSalva = tarefaRepository.save(tarefaAtualizada);
        return ResponseEntity.ok(tarefaSalva);
    }

    // Deletar tarefa por ID (Retorna 204 No Content ou 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}