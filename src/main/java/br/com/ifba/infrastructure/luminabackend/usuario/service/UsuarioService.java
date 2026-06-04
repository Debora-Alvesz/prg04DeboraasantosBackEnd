package br.com.ifba.infrastructure.luminabackend.usuario.service;

import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import br.com.ifba.infrastructure.luminabackend.usuario.repository.UsuarioRepository;
import br.com.ifba.infrastructure.luminabackend.exception.ObjetoNaoEncontradoException; // IMPORT ADICIONADO AQUI
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // C - Cadastra um novo usuário
    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // R - Lista todos os usuários cadastrados (Com Paginação)
    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    // R - Buscar usuário por ID (Dispara erro se não encontrar)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário com o ID " + id + " não foi encontrado."));
    }

    // U - Atualizar dados do usuário (Dispara erro se não encontrar)
    @Transactional
    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        // Busca o usuário existente, se não achar, dispara o erro na hora
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível atualizar. Usuário com o ID " + id + " não existe."));

        // Transfere os dados novos
        usuarioExistente.setNome(dadosAtualizados.getNome());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());
        usuarioExistente.setSenha(dadosAtualizados.getSenha());
        usuarioExistente.setRole(dadosAtualizados.getRole());

        return usuarioRepository.save(usuarioExistente);
    }

    // D - Deletar um usuário por ID (Dispara erro se não encontrar)
    @Transactional
    public void deletar(Long id) {
        // Se o ID não existir, dispara o erro antes de tentar deletar
        if (!usuarioRepository.existsById(id)) {
            throw new ObjetoNaoEncontradoException("Não foi possível deletar. Usuário com o ID " + id + " não existe.");
        }
        usuarioRepository.deleteById(id);
    }

    // Método extra para o login simples (busca por email e senha)
    public Optional<Usuario> realizarLogin(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
}