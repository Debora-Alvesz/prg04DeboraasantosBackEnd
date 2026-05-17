package com.lumina.luminabackend.tarefas.controller;

import com.lumina.luminabackend.tarefas.model.Tarefa;
import com.lumina.luminabackend.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Avisa ao Spring que esta classe gerencia rotas de API
@RequestMapping("/tarefas") // Define que a rota base será http://localhost:8080/tarefas
public class TarefaController {

    @Autowired // Injeta o repository criado no Passo 1 para usarmos aqui
    private TarefaRepository tarefaRepository;

    // ROTA 1: Buscar todas as tarefas do banco (GET)
    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    // ROTA 2: Criar uma nova tarefa (POST) com status code 201 Created
    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa novaTarefa) {
        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }
}