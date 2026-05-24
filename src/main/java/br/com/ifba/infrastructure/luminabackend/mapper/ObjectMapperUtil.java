package br.com.ifba.infrastructure.luminabackend.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

// Diz ao Spring que esta é uma classe utilitária que pode ser injetada em outros lugares (como nos Controllers)
@Component
public class ObjectMapperUtil {

    // Cria a instância do ModelMapper que fará as conversões de objetos
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    // Bloco de configuração que roda uma única vez ao iniciar a classe
    static {
        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true) // Ignora ambiguidades se houver campos com nomes parecidos
                .setMatchingStrategy(MatchingStrategies.STRICT) // Exige que os nomes dos campos sejam exatamente iguais para mapear
                .setFieldMatchingEnabled(true) // Ativa o mapeamento direto de campos (atributos)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE); // Permite acessar campos privados sem precisar de getters/setters vazios
    }

    // Método que converte UM único objeto (Ex: converte UsuarioRequestDto para Usuario)
    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {
        return MODEL_MAPPER.map(object, clazz);
    }

    // Método que converte uma LISTA de objetos (Ex: converte List<Usuario> para List<UsuarioGetResponseDto>)
    public <Input, Output> List<Output> mapAll(final Collection<Input> collection, final Class<Output> clazz) {
        return collection.stream()
                .map(element -> map(element, clazz)) // Converte elemento por elemento da lista
                .toList(); // Transforma o resultado de volta em uma Lista corrigida
    }
}