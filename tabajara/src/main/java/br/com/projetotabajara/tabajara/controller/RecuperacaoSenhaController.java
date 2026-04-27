package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.projetotabajara.tabajara.service.UsuarioService;

@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/esqueci-senha")
    public String esqueciSenha() {
        return "esqueci-senha";
    }

    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(@RequestParam String email, Model model) {
        String token = usuarioService.gerarTokenRecuperacao(email);
        if (token == null) {
            model.addAttribute("erro", "Email nao encontrado.");
            return "esqueci-senha";
        }

        String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/redefinir-senha")
                .queryParam("token", token)
                .toUriString();

        model.addAttribute("mensagem", "Link de recuperacao (simulacao): " + link);
        return "esqueci-senha";
    }

    @GetMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    public String salvarNovaSenha(@RequestParam String token,
            @RequestParam String senha,
            @RequestParam(required = false) String confirmarSenha,
            Model model) {
        if (confirmarSenha != null && !senha.equals(confirmarSenha)) {
            model.addAttribute("erro", "As senhas nao conferem.");
            model.addAttribute("token", token);
            return "redefinir-senha";
        }

        boolean sucesso = usuarioService.redefinirSenha(token, senha);
        if (!sucesso) {
            model.addAttribute("erro", "Token invalido ou expirado.");
            model.addAttribute("token", token);
            return "redefinir-senha";
        }

        return "redirect:/login?senhaRedefinida";
    }
}
