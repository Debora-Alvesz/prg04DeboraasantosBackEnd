package br.com.ifba.infrastructure.luminabackend.categoria.controller;

import br.com.ifba.infrastructure.luminabackend.categoria.entity.Categoria;
import br.com.ifba.infrastructure.luminabackend.categoria.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Define a rota base para categorias
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService; // Conecta com as regras de negócio de categoria

    // C - Criar uma nova categoria (Retorna 201 Created)
    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody Categoria novaCategoria) {
        Categoria categoriaSalva = categoriaService.criar(novaCategoria);
        return new ResponseEntity<>(categoriaSalva, HttpStatus.CREATED);
    }

    // R - Listar todas as categorias (Retorna 200 OK)
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    // U - Editar uma categoria existente por ID (Retorna 200 OK ou 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody Categoria dadosAtualizados) {
        return categoriaService.atualizar(id, dadosAtualizados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // D - Deletar uma categoria por ID (Retorna 204 No Content ou 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (categoriaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}