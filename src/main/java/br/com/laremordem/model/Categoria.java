package br.com.laremordem.model;

import jakarta.persistence.*;

@Entity // Classe que representa uma tabela.
@Table(name = "categorias")
public class Categoria {
    @Id // Chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Nome cadastrado.

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
