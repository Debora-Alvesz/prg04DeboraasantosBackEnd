package br.com.ifba.infrastructure.luminabackend.usuario.controller;

import br.com.ifba.infrastructure.luminabackend.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.infrastructure.luminabackend.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.infrastructure.luminabackend.usuario.entity.Usuario;
import br.com.ifba.infrastructure.luminabackend.usuario.service.UsuarioService;
import br.com.ifba.infrastructure.luminabackend.mapper.ObjectMapperUtil;
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

    @Autowired
    private ObjectMapperUtil objectMapperUtil; // Injeta o utilitário de conversão (DTOs)

    // C - Cadastrar um novo usuário (Recebe RequestDto e devolve GetResponseDto sem a senha)
    @PostMapping
    public ResponseEntity<UsuarioGetResponseDto> cadastrar(@RequestBody UsuarioPostRequestDto dto) {
        // 1. Converte o DTO recebido para a Entidade Usuario
        Usuario usuarioEntity = objectMapperUtil.map(dto, Usuario.class);

        // 2. Salva no banco de dados através do Service
        Usuario usuarioSalvo = usuarioService.cadastrar(usuarioEntity);

        // 3. Converte a entidade salva para o DTO de resposta (escondendo a senha e o ID)
        UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioSalvo, UsuarioGetResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // R - Listar todos os usuários (Converte a lista de entidades para lista de GetResponseDto)
    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDto>> listarTodos() {
        // 1. Busca a lista de entidades do Service
        List<Usuario> usuarios = usuarioService.listarTodos();

        // 2. Converte toda a lista de Usuario para uma lista de UsuarioGetResponseDto
        List<UsuarioGetResponseDto> responseList = objectMapperUtil.mapAll(usuarios, UsuarioGetResponseDto.class);

        return ResponseEntity.ok(responseList);
    }

    // R - Buscar usuário por ID (Devolve GetResponseDto protegido se encontrar)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> buscarPorId(@PathVariable Long id) {
        // Busca no service e, se existir, converte para DTO antes de responder
        return usuarioService.buscarPorId(id)
                .map(usuarioEntity -> {
                    UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioEntity, UsuarioGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 caso não exista
    }

    // U - Editar dados do usuário por ID (Recebe RequestDto e devolve GetResponseDto protegido)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> atualizar(@PathVariable Long id, @RequestBody UsuarioPostRequestDto dto) {
        // 1. Converte os dados recebidos no DTO para a Entidade Usuario
        Usuario dadosAtualizados = objectMapperUtil.map(dto, Usuario.class);

        // Garante que a entidade saiba qual ID ela está atualizando
        dadosAtualizados.setId(id);

        // 2. Executa a atualização chamando o SERVICE e converte o resultado caso ele exista no banco
        return usuarioService.atualizar(id, dadosAtualizados)
                .map(usuarioEntity -> {
                    UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioEntity, UsuarioGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar um usuário por ID (Não usa DTO pois o retorno é vazio 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.deletar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 Sucesso
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
    }

    // Método extra: Login simples (Recebe RequestDto com email/senha e devolve GetResponseDto sem senha)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioPostRequestDto dto) {
        // Tenta realizar o login usando o email e a senha que vieram no DTO
        return usuarioService.realizarLogin(dto.getEmail(), dto.getSenha())
                .map(usuarioEntity -> {
                    // Se o login der certo, converte o usuário para DTO ocultando a senha
                    UsuarioGetResponseDto responseDto = objectMapperUtil.map(usuarioEntity, UsuarioGetResponseDto.class);
                    return ResponseEntity.ok(responseDto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // Retorna 401 se errar as credenciais
    }
}