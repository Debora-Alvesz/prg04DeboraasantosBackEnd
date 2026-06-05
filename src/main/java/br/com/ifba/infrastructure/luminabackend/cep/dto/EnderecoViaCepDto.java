package br.com.ifba.infrastructure.luminabackend.cep.dto;

import lombok.Data;

@Data // O Lombok cria automaticamente os Getters, Setters e o Construtor para nós
public class EnderecoViaCepDto {

    // Estes atributos têm o exato mesmo nome dos campos que a API do ViaCEP devolve
    private String cep;
    private String logradouro; // Nome da rua
    private String complemento;
    private String bairro;
    private String localidade; // Nome da cidade
    private String uf; // Estado (Ex: SP, BA)
}