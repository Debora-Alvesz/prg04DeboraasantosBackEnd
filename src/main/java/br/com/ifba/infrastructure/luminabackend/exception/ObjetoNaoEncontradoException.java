package br.com.ifba.infrastructure.luminabackend.exception;

// Esta classe representa o erro específico de quando algo não é achado no banco
public class ObjetoNaoEncontradoException extends RuntimeException {
    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem); // Passa a mensagem de erro para a classe pai
    }
}