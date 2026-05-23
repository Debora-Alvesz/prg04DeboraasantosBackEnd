package br.com.ifba.infrastructure.luminabackend.tarefas.repository;

import br.com.ifba.infrastructure.luminabackend.tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// O JpaRepository já vem com comandos prontos como salvar, deletar e buscar
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Busca tarefas de um status específico (ex: "Concluído")
    List<Tarefa> findByStatus(String status);
}