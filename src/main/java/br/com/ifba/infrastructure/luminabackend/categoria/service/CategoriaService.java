package br.com.ifba.infrastructure.luminabackend.categoria.service;

import br.com.ifba.infrastructure.luminabackend.categoria.entity.Categoria;
import br.com.ifba.infrastructure.luminabackend.categoria.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // C - Salva uma nova categoria de tarefas
    @Transactional
    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // R - Lista todas as categorias existentes
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // U - Atualiza o nome de uma categoria se ela existir no banco
    @Transactional
    public Optional<Categoria> atualizar(Long id, Categoria dadosAtualizados) {
        if (!categoriaRepository.existsById(id)) {
            return Optional.empty(); // Retorna vazio se não encontrar a categoria
        }
        dadosAtualizados.setId(id); // Garante que vai salvar por cima do ID certo
        return Optional.of(categoriaRepository.save(dadosAtualizados));
    }

    // D - Deleta uma categoria do banco usando o ID
    @Transactional
    public boolean deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return false; // Retorna falso se a categoria não existir
        }
        categoriaRepository.deleteById(id);
        return true; // Retorna verdadeiro se deletou com sucesso
    }
}