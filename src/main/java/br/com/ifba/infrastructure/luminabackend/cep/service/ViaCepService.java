package br.com.ifba.infrastructure.luminabackend.cep.service;

import br.com.ifba.infrastructure.luminabackend.cep.dto.EnderecoViaCepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service // Indica que esta classe contém regras de negócio
public class ViaCepService {

    @Autowired
    private WebClient webClient; // Ferramenta do Spring que faz requisições na internet

    // Método que recebe um CEP e devolve o endereço preenchido
    public EnderecoViaCepDto buscarEnderecoPorCep(String cep) {

        return webClient
                .get() // Diz que vamos fazer uma busca (requisição do tipo GET)
                .uri("https://viacep.com.br/ws/" + cep + "/json/") // Monta o link da API com o CEP digitado
                .retrieve() // Dispara a requisição para a internet
                .bodyToMono(EnderecoViaCepDto.class) // Pega o JSON de resposta e converte no nosso DTO
                .block(); // Espera a resposta da internet chegar antes de continuar o código
    }
}