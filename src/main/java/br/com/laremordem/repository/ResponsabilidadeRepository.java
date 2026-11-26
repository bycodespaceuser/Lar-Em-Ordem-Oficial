package br.com.laremordem.repository;

import br.com.laremordem.model.Responsabilidade;
import org.springframework.data.jpa.repository.JpaRepository;

// Métodos de cadastro, consulta, edição e exclusão.
public interface ResponsabilidadeRepository extends JpaRepository<Responsabilidade, Long> {
    long countByConcluidaTrue(); // Conta as atribuições concluídas.

}
