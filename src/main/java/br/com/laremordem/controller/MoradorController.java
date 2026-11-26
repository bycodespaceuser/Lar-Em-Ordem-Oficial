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
@RequestMapping("/moradores")
public class MoradorController {
    private final MoradorRepository repository;

    public MoradorController(MoradorRepository repository) {
        this.repository = repository;
    }

    @GetMapping // Abre a listagem e o formulário.
    public String listar(Model model) {
        model.addAttribute("registro", new Morador());
        return pagina(model);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("registro", buscar(id));
        return pagina(model);
    }

    private String pagina(Model model) {
        model.addAttribute("registros", repository.findAll()); // Dados para o HTML.
        return "moradores";
    }

    @PostMapping("/salvar") // Recebe os dados do formulário.
    public String salvar(@RequestParam(required = false) Long id, @RequestParam String nome, RedirectAttributes redirect) {
        if (nome.isBlank() || nome.length() > 255) {
            redirect.addFlashAttribute("erro", "Informe um nome válido (até 255 caracteres).");
            return "redirect:/moradores";
        }
        Morador registro = id == null ? new Morador() : buscar(id);
        registro.setNome(nome.trim());
        repository.save(registro); // Grava no H2.
        return "redirect:/moradores";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            repository.deleteById(id); // Exclui pelo ID.
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("erro", "Exclua primeiro os registros vinculados.");
        }
        return "redirect:/moradores";
    }

    private Morador buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
