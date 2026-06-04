package br.com.ifba.infrastructure.luminabackend.projeto.service;

import br.com.ifba.infrastructure.luminabackend.projeto.entity.Projeto;
import br.com.ifba.infrastructure.luminabackend.projeto.repository.ProjetoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service // Avisa ao Spring que esta é uma classe de serviço de Projetos
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    // C - Salva um novo projeto
    @Transactional
    public Projeto criar(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    // R - Retorna todos os projetos para listar no front (Com Paginação)
    public Page<Projeto> listarTodos(Pageable pageable) {
        return projetoRepository.findAll(pageable);
    }

    // R - Busca um único projeto pelo ID
    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    // U - Atualiza os dados de um projeto se ele existir
    @Transactional
    public Optional<Projeto> atualizar(Long id, Projeto dadosAtualizados) {
        if (!projetoRepository.existsById(id)) {
            return Optional.empty(); // Retorna vazio se o projeto não existir
        }
        dadosAtualizados.setId(id); // Garante que vai salvar por cima do ID certo
        return Optional.of(projetoRepository.save(dadosAtualizados));
    }

    // D - Deleta um projeto por ID
    @Transactional
    public boolean deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            return false;
        }
        projetoRepository.deleteById(id);
        return true;
    }
}