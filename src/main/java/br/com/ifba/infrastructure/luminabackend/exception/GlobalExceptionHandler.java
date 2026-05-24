package br.com.ifba.infrastructure.luminabackend.exception; // Mesma pasta global de exceções

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice // Avisa ao Spring que esta classe captura os erros de todo o sistema
public class GlobalExceptionHandler {

    // Método que captura especificamente erros de e-mail duplicado (violação no banco)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroRespostaDto> tratarBancoDeDados(DataIntegrityViolationException ex, HttpServletRequest request) {

        // Cria a caixinha de erro preenchida com os dados corretos
        ErroRespostaDto erro = new ErroRespostaDto(
                LocalDateTime.now(),                           // Hora exata do erro
                HttpStatus.BAD_REQUEST.value(),                // Código 400 (Requisição incorreta)
                "Erro de integridade no banco de dados",       // Título simples do erro
                "O e-mail informado já está cadastrado no sistema.", // Mensagem amigável para o usuário
                request.getRequestURI()                        // Rota que deu erro (ex: /api/usuario)
        );

        // Devolve o erro formatado em JSON com o status 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Método que captura especificamente quando um objeto/id não é encontrado
    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDto> tratarObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, HttpServletRequest request) {

        // Cria a nossa caixinha de erro padrão
        ErroRespostaDto erro = new ErroRespostaDto(
                LocalDateTime.now(),                           // Hora exata do erro
                HttpStatus.NOT_FOUND.value(),                  // Código 404 (Não Encontrado)
                "Recurso não encontrado",                      // Título simples do erro
                ex.getMessage(),                               // Mensagem dinâmica que vem do Service
                request.getRequestURI()                        // Rota que deu erro (ex: /api/usuario/999)
        );

        // Devolve o JSON com o status 404 (Not Found)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}