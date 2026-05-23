package br.com.ifba.infraestructure.luminabackend.tarefas.repository;

import br.com.ifba.infraestructure.luminabackend.tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// O JpaRepository já vem com comandos prontos como salvar, deletar e buscar
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}