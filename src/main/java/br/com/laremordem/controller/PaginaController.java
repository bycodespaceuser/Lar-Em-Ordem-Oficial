package br.com.laremordem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {
    @GetMapping("/")
    public String inicio() {
        return "inicio"; // Abre a página inicial.
    }
}
