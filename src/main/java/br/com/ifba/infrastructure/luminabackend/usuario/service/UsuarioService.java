package br.com.ifba.infrastructure.luminabackend.usuario.service;

import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import br.com.ifba.infrastructure.luminabackend.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // C - Cadastra um novo usuário
    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // R - Lista todos os usuários cadastrados
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // R - Busca um usuário pelo ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // U - Atualiza os dados de um usuário (nome, senha, foto, etc.) se ele existir
    public Optional<Usuario> atualizar(Long id, Usuario dadosAtualizados) {
        if (!usuarioRepository.existsById(id)) {
            return Optional.empty(); // Retorna vazio se o usuário não existir
        }
        dadosAtualizados.setId(id); // Garante que vai salvar por cima do ID certo
        return Optional.of(usuarioRepository.save(dadosAtualizados));
    }

    // D - Deleta um usuário do banco pelo ID
    public boolean deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    // Método extra para o login simples (busca por email e senha)
    public Optional<Usuario> realizarLogin(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
}