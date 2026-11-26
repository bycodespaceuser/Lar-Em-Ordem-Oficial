package br.com.laremordem.service;

import br.com.laremordem.repository.ResponsabilidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class ResponsabilidadeService {
    private final ResponsabilidadeRepository repository;

    public ResponsabilidadeService(ResponsabilidadeRepository repository) {
        this.repository = repository;
    }

    public long total() {
        return repository.count(); // Total de atribuições.
    }

    public long concluidas() {
        return repository.countByConcluidaTrue(); // Tarefas finalizadas.
    }

    public long pendentes() {
        return total() - concluidas(); // Calcula o que falta.
    }
}
