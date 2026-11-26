package br.com.laremordem;

import br.com.laremordem.model.*;
import br.com.laremordem.repository.*;
import br.com.laremordem.service.ResponsabilidadeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class LarEmOrdemApplicationTests {
    @Autowired MoradorRepository moradores;
    @Autowired ComodoRepository comodos;
    @Autowired CategoriaRepository categorias;
    @Autowired TarefaRepository tarefas;
    @Autowired ResponsabilidadeRepository responsabilidades;
    @Autowired ResponsabilidadeService service;

    @Test
    void contabilizaResponsabilidades() {
        Morador morador = new Morador(); morador.setNome("Maria"); moradores.save(morador);
        Comodo comodo = new Comodo(); comodo.setNome("Cozinha"); comodos.save(comodo);
        Categoria categoria = new Categoria(); categoria.setNome("Limpeza"); categorias.save(categoria);
        Tarefa tarefa = new Tarefa(); tarefa.setNome("Lavar louça"); tarefa.setComodo(comodo); tarefa.setCategoria(categoria); tarefas.save(tarefa);
        Responsabilidade r = new Responsabilidade(); r.setMorador(morador); r.setTarefa(tarefa); responsabilidades.save(r);
        assertEquals(1, service.total());
        assertEquals(1, service.pendentes());
        r.setConcluida(true); responsabilidades.save(r);
        assertEquals(1, service.concluidas());
        assertEquals(0, service.pendentes());
    }
}
