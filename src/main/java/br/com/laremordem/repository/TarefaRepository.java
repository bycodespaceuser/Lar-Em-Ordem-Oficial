package br.com.laremordem.repository;

import br.com.laremordem.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

// Métodos de cadastro, consulta, edição e exclusão.
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
