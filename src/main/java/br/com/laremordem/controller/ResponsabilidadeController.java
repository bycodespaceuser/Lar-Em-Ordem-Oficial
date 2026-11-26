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
import br.com.laremordem.service.ResponsabilidadeService;

@Controller
@RequestMapping("/responsabilidades")
public class ResponsabilidadeController {
    private final ResponsabilidadeRepository repository;
    private final MoradorRepository moradorRepository;
    private final TarefaRepository tarefaRepository;
    private final ResponsabilidadeService service;

    public ResponsabilidadeController(ResponsabilidadeRepository repository, MoradorRepository moradorRepository, TarefaRepository tarefaRepository, ResponsabilidadeService service) {
        this.repository = repository;
        this.moradorRepository = moradorRepository;
        this.tarefaRepository = tarefaRepository;
        this.service = service;
    }

    @GetMapping // Abre a listagem e o formulário.
    public String listar(Model model) {
        model.addAttribute("registro", new Responsabilidade());
        return pagina(model);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("registro", buscar(id));
        return pagina(model);
    }

    private String pagina(Model model) {
        model.addAttribute("registros", repository.findAll()); // Dados para o HTML.
        model.addAttribute("moradores", moradorRepository.findAll());
        model.addAttribute("tarefas", tarefaRepository.findAll());
        model.addAttribute("total", service.total());
        model.addAttribute("concluidas", service.concluidas());
        model.addAttribute("pendentes", service.pendentes());
        return "responsabilidades";
    }

    @PostMapping("/salvar") // Recebe os dados do formulário.
    public String salvar(@RequestParam(required = false) Long id, @RequestParam(defaultValue = "false") boolean concluida, @RequestParam Long moradorId, @RequestParam Long tarefaId, RedirectAttributes redirect) {
        Responsabilidade registro = id == null ? new Responsabilidade() : buscar(id);
        registro.setConcluida(concluida);
        registro.setMorador(moradorRepository.findById(moradorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        registro.setTarefa(tarefaRepository.findById(tarefaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        repository.save(registro); // Grava no H2.
        return "redirect:/responsabilidades";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            repository.deleteById(id); // Exclui pelo ID.
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("erro", "Exclua primeiro os registros vinculados.");
        }
        return "redirect:/responsabilidades";
    }

    @PostMapping("/alternar/{id}")
    public String alternar(@PathVariable Long id) {
        Responsabilidade registro = buscar(id);
        registro.setConcluida(!registro.isConcluida());
        repository.save(registro);
        return "redirect:/responsabilidades";
    }

    private Responsabilidade buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
