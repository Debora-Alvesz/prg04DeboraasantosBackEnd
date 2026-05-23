package br.com.ifba.infrastructure.luminabackend.categoria.repository;

import br.com.ifba.infrastructure.luminabackend.categoria.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Conecta a entidade Categoria ao banco de dados
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca uma categoria através do nome dela
    Optional<Categoria> findByNome(String nome);
}