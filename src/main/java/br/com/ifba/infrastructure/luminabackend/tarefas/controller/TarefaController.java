package br.com.ifba.infrastructure.luminabackend.tarefas.controller;

import br.com.ifba.infrastructure.luminabackend.tarefas.entity.Tarefa;
import br.com.ifba.infrastructure.luminabackend.tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas") // Define a rota padrão exigida no documento do projeto
public class TarefaController {

    @Autowired
    private TarefaService tarefaService; // Conecta com a camada de serviço (regras de negócio)

    // C - Criar uma nova tarefa (Retorna status 201 Created)
    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa novaTarefa) {
        Tarefa tarefaSalva = tarefaService.criar(novaTarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }

    // R - Listar todas as tarefas cadastradas (Retorna status 200 OK)
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    // R - Buscar uma única tarefa por ID (Retorna 200 OK ou 404 caso não exista)
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // U - Atualizar uma tarefa existente (Retorna 200 OK ou 404 caso o ID não exista)
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa dadosAtualizados) {
        return tarefaService.atualizar(id, dadosAtualizados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar uma tarefa por ID (Retorna 204 No Content ou 404 caso o ID não exista)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (tarefaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}