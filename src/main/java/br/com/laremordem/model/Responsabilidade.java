package br.com.laremordem.model;

import jakarta.persistence.*;

@Entity // Classe que representa uma tabela.
@Table(name = "responsabilidades")
public class Responsabilidade {
    @Id // Chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean concluida; // Situação da tarefa atribuída.

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id", nullable = false) // Chave estrangeira.
    private Morador morador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tarefa_id", nullable = false) // Chave estrangeira.
    private Tarefa tarefa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public Morador getMorador() { return morador; }
    public void setMorador(Morador morador) { this.morador = morador; }

    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
}
