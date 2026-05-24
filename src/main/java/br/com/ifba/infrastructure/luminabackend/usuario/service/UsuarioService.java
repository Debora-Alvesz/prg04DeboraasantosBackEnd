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
        // 1. Busca o usuário já existente no banco de dados
        return usuarioRepository.findById(id).map(usuarioExistente -> {

            // 2. Transfere os dados novos do Postman para o usuário que já existe
            usuarioExistente.setNome(dadosAtualizados.getNome());
            usuarioExistente.setEmail(dadosAtualizados.getEmail());
            usuarioExistente.setSenha(dadosAtualizados.getSenha());
            usuarioExistente.setRole(dadosAtualizados.getRole());

            // Se a sua entidade tiver o campo 'foto', descomente a linha abaixo:
            // usuarioExistente.setFoto(dadosAtualizados.getFoto());

            // 3. Salva o objeto mesclado (O JPA fará um UPDATE seguro sem erro de e-mail duplicado)
            return usuarioRepository.save(usuarioExistente);
        });
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