package br.com.ifba.infrastructure.luminabackend.usuario.repository;

import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Conecta a entidade Usuario ao banco de dados
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca um usuário combinando Email e Senha (usado no login)
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}