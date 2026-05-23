package br.com.ifba.infraestructure.luminabackend.categoria.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {

    // Gerador automático de ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome ou etiqueta da categoria (ex: Trabalho, Estudos)
    @Column(nullable = false)
    private String nome;
}