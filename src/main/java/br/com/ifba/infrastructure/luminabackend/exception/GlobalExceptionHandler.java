package br.com.ifba.infrastructure.luminabackend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de e-mail duplicado ou violações de banco de dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroRespostaDto> tratarBancoDeDados(DataIntegrityViolationException ex, HttpServletRequest request) {
        ErroRespostaDto erro = new ErroRespostaDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de integridade no banco de dados",
                "O e-mail informado já está cadastrado no sistema.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Captura erros quando um registro ou ID não é localizado
    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDto> tratarObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, HttpServletRequest request) {
        ErroRespostaDto erro = new ErroRespostaDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Captura e detalha os erros de campos inválidos ou vazios enviados nos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErrosValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Cria um mapa para guardar o nome do campo e a mensagem de erro
        Map<String, String> errosDosCampos = new HashMap<>();

        // Pega todos os erros de campos gerados pelo Spring e adiciona no mapa
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            errosDosCampos.put(erro.getField(), erro.getDefaultMessage());
        }

        // Monta a estrutura final do JSON de resposta estruturado
        Map<String, Object> respostaDeErro = new HashMap<>();
        respostaDeErro.put("timestamp", LocalDateTime.now());
        respostaDeErro.put("status", HttpStatus.BAD_REQUEST.value());
        respostaDeErro.put("erro", "Erro de validação de campos");
        respostaDeErro.put("campos_invalidos", errosDosCampos);
        respostaDeErro.put("caminho", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaDeErro);
    }
}