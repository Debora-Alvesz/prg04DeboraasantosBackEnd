package br.com.ifba.infrastructure.luminabackend.projeto.service;

import br.com.ifba.infrastructure.luminabackend.projeto.entity.Projeto;
import br.com.ifba.infrastructure.luminabackend.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Avisa ao Spring que esta é uma classe de serviço de Projetos
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    // C - Salva um novo projeto
    public Projeto criar(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    // R - Retorna todos os projetos para listar no front
    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    // R - Busca um único projeto pelo ID
    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    // U - Atualiza os dados de um projeto se ele existir
    public Optional<Projeto> atualizar(Long id, Projeto dadosAtualizados) {
        if (!projetoRepository.existsById(id)) {
            return Optional.empty(); // Retorna vazio se o projeto não existir
        }
        dadosAtualizados.setId(id); // Garante que vai salvar por cima do ID certo
        return Optional.of(projetoRepository.save(dadosAtualizados));
    }

    // D - Deleta um projeto por ID
    public boolean deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            return false;
        }
        projetoRepository.deleteById(id);
        return true;
    }
}