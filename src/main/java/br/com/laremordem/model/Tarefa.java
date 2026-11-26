package br.com.laremordem.model;

import jakarta.persistence.*;

@Entity // Classe que representa uma tabela.
@Table(name = "tarefas")
public class Tarefa {
    @Id // Chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Nome cadastrado.

    @ManyToOne(optional = false)
    @JoinColumn(name = "comodo_id", nullable = false) // Chave estrangeira.
    private Comodo comodo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false) // Chave estrangeira.
    private Categoria categoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Comodo getComodo() { return comodo; }
    public void setComodo(Comodo comodo) { this.comodo = comodo; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
