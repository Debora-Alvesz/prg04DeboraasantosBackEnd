package br.com.ifba.infrastructure.luminabackend.usuario.controller;

import br.com.ifba.infrastructure.luminabackend.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.infrastructure.luminabackend.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import br.com.ifba.infrastructure.luminabackend.usuario.service.UsuarioService;
import br.com.ifba.infrastructure.luminabackend.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario") // Rota base para usuários
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Conecta com as regras de negócio

    @Autowired
    private ObjectMapperUtil objectMapperUtil; // Utilitário de conversão (DTOs)

    // C - Cadastrar um novo usuário
    @PostMapping
    public ResponseEntity<UsuarioGetResponseDto> cadastrar( @Valid @RequestBody UsuarioPostRequestDto dto) {
        Usuario usuarioEntity = objectMapperUtil.map(dto, Usuario.class);
        Usuario usuarioSalvo = usuarioService.cadastrar(usuarioEntity);
        UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioSalvo, UsuarioGetResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // R - Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDto>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioGetResponseDto> responseList = objectMapperUtil.mapAll(usuarios, UsuarioGetResponseDto.class);
        return ResponseEntity.ok(responseList);
    }

    // R - Buscar usuário por ID (Mais simples: o service já joga erro se não achar)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> buscarPorId(@PathVariable Long id) {
        Usuario usuarioEntity = usuarioService.buscarPorId(id);
        UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioEntity, UsuarioGetResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    // U - Editar dados do usuário por ID
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioPostRequestDto dto) {
        Usuario dadosAtualizados = objectMapperUtil.map(dto, Usuario.class);
        dadosAtualizados.setId(id);

        Usuario usuarioSalvo = usuarioService.atualizar(id, dadosAtualizados);
        UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioSalvo, UsuarioGetResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    // D - Deletar um usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id); // O Service joga erro se o ID não existir
        return ResponseEntity.noContent().build(); // Retorna 204 Sucesso
    }

    // Método extra: Login simples
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioPostRequestDto dto) {
        // Se o login falhar, o ideal é tratar no service depois, mas por enquanto mantemos o retorno direto
        return usuarioService.realizarLogin(dto.getEmail(), dto.getSenha())
                .map(usuarioEntity -> {
                    UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioEntity, UsuarioGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}