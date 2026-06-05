package br.com.ifba.infrastructure.luminabackend.cep.controller;

import br.com.ifba.infrastructure.luminabackend.cep.dto.EnderecoViaCepDto;
import br.com.ifba.infrastructure.luminabackend.cep.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Diz que esta classe vai responder requisições da web
@RequestMapping("/api/cep") // O endereço base no nosso sistema para buscar CEP
public class CepController {

    @Autowired
    private ViaCepService viaCepService; // Chama o Service que criamos acima

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoViaCepDto> consultarCep(@PathVariable String cep) {
        try {
            // Tenta buscar o endereço na internet
            EnderecoViaCepDto endereco = viaCepService.buscarEnderecoPorCep(cep);

            // Se o ViaCEP devolver vazio (CEP não existe)
            if (endereco == null || endereco.getCep() == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found
            }

            return ResponseEntity.ok(endereco); // Retorna 200 OK

        } catch (Exception e) {
            // Se o ViaCEP der erro (ex: formato do CEP totalmente inválido) ou a internet cair
            return ResponseEntity.badRequest().build(); // Retorna 400 Bad Request
        }
    }
}