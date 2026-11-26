package br.com.laremordem.controller;

import br.com.laremordem.model.*;
import br.com.laremordem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaRepository repository;
    private final ComodoRepository comodoRepository;
    private final CategoriaRepository categoriaRepository;

    public TarefaController(TarefaRepository repository, ComodoRepository comodoRepository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.comodoRepository = comodoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping // Abre a listagem e o formulário.
    public String listar(Model model) {
        model.addAttribute("registro", new Tarefa());
        return pagina(model);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("registro", buscar(id));
        return pagina(model);
    }

    private String pagina(Model model) {
        model.addAttribute("registros", repository.findAll()); // Dados para o HTML.
        model.addAttribute("comodos", comodoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "tarefas";
    }

    @PostMapping("/salvar") // Recebe os dados do formulário.
    public String salvar(@RequestParam(required = false) Long id, @RequestParam String nome, @RequestParam Long comodoId, @RequestParam Long categoriaId, RedirectAttributes redirect) {
        if (nome.isBlank() || nome.length() > 255) {
            redirect.addFlashAttribute("erro", "Informe um nome válido (até 255 caracteres).");
            return "redirect:/tarefas";
        }
        Tarefa registro = id == null ? new Tarefa() : buscar(id);
        registro.setNome(nome.trim());
        registro.setComodo(comodoRepository.findById(comodoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        registro.setCategoria(categoriaRepository.findById(categoriaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        repository.save(registro); // Grava no H2.
        return "redirect:/tarefas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            repository.deleteById(id); // Exclui pelo ID.
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("erro", "Exclua primeiro os registros vinculados.");
        }
        return "redirect:/tarefas";
    }

    private Tarefa buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
