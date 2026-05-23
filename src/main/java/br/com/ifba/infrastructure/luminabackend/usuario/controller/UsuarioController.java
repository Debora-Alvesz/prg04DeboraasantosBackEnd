package br.com.ifba.infrastructure.luminabackend.usuario.controller;

import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import br.com.ifba.infrastructure.luminabackend.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario") // Define a rota base para usuários
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Conecta com as regras de negócio de usuário

    // C - Cadastrar um novo usuário (Retorna 201 Created)
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario novoUsuario) {
        Usuario usuarioSalvo = usuarioService.cadastrar(novoUsuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    // R - Listar todos os usuários (Retorna 200 OK)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // R - Buscar usuário por ID (Retorna 200 OK ou 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // U - Editar dados do usuário por ID (Retorna 200 OK ou 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario dadosAtualizados) {
        return usuarioService.atualizar(id, dadosAtualizados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar um usuário por ID (Retorna 204 No Content ou 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método extra: Login simples por Email e Senha (Retorna 200 OK ou 401 Unauthorized)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario dadosLogin) {
        return usuarioService.realizarLogin(dadosLogin.getEmail(), dadosLogin.getSenha())
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}