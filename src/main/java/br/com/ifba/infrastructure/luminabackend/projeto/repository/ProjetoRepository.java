package br.com.ifba.infrastructure.luminabackend.projeto.repository;

import br.com.ifba.infrastructure.luminabackend.projeto.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Conecta a entidade Projeto ao banco de dados
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Já possui todos os métodos básicos de CRUD salvos automaticamente
}
