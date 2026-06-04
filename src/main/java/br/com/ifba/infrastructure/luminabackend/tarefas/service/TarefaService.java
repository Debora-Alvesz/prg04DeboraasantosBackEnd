package br.com.ifba.infrastructure.luminabackend.tarefas.service;

import br.com.ifba.infrastructure.luminabackend.tarefas.entity.Tarefa;
import br.com.ifba.infrastructure.luminabackend.tarefas.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service // Avisa ao Spring que esta é uma classe de serviço (regras de negócio)
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Salva uma nova tarefa no banco
    @Transactional
    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    // Retorna todas as tarefas (Com Paginação)
    public Page<Tarefa> listarTodas(Pageable pageable) {
        return tarefaRepository.findAll(pageable);
    }

    // Busca uma tarefa por ID
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    // Atualiza uma tarefa se ela existir
    @Transactional
    public Optional<Tarefa> atualizar(Long id, Tarefa dadosAtualizados) {
        if (!tarefaRepository.existsById(id)) {
            return Optional.empty(); // Retorna vazio se não achar o ID
        }
        dadosAtualizados.setId(id); // Garante que vai atualizar o ID correto
        return Optional.of(tarefaRepository.save(dadosAtualizados));
    }

    // Deleta uma tarefa por ID
    @Transactional
    public boolean deletar(Long id) {
        if (!tarefaRepository.existsById(id)) {
            return false; // Retorna falso se não existir
        }
        tarefaRepository.deleteById(id);
        return true; // Retorna verdadeiro se deletou com sucesso
    }
}