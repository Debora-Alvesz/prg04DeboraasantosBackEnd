package com.lumina.luminabackend.tarefas.repository;

import com.lumina.luminabackend.tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// O JpaRepository já vem com comandos prontos como salvar, deletar e buscar
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}