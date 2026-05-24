package br.com.ifba.infrastructure.luminabackend.exception; // Pasta global de exceções

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroRespostaDto {
    private LocalDateTime timestamp; // Guarda o dia e a hora do erro
    private int status;              // Guarda o código HTTP (ex: 400, 404)
    private String erro;            // Guarda o título do erro
    private String mensagem;        // Guarda a explicação do erro
    private String caminho;         // Guarda a URL acessada (ex: /api/usuario)
}