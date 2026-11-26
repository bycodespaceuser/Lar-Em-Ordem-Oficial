package br.com.laremordem.repository;

import br.com.laremordem.model.Comodo;
import org.springframework.data.jpa.repository.JpaRepository;

// Métodos de cadastro, consulta, edição e exclusão.
public interface ComodoRepository extends JpaRepository<Comodo, Long> {
}
