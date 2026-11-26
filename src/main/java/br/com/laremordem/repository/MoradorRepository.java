package br.com.laremordem.repository;

import br.com.laremordem.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

// Métodos de cadastro, consulta, edição e exclusão.
public interface MoradorRepository extends JpaRepository<Morador, Long> {
}
