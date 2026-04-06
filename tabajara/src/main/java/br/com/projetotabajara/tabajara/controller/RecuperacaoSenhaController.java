package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.projetotabajara.tabajara.service.UsuarioService;

public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    // Tela de "Esqueci minha senha"
    @GetMapping("/esqueci-senha")
    public String esqueciSenha() {
        return "esqueci-senha";
    }

    // Processar o pedido de recuperação de senha
    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(@RequestParam String email, Model model) {
        String token = usuarioService.gerarTokenRecuperacao(email);
        if (token == null) {
            model.addAttribute("erro", "Email não encontrado.");
            return "esqueci-senha";
        }

        String link = "http://localhost:8080/redefinir-senha?token=" + token;
        model.addAttribute("mensagem", "Link de recuperação(simulação): " + link);
        return "esqueci-senha";

    }

    // Tela de redefinição de senha
    @GetMapping("/redefinir-senha")

    public String redefinirSenha(@RequestParam String token, Model model) {
        model.addAttribute(token, token);
        return "redefinir-senha";

    }

    // Salvar nova senha
    @PostMapping("/redefinir-senha")
    public String salvarNovaSenha(@RequestParam String token, @RequestParam String senha, Model model) {
        boolean sucesso = usuarioService.redefinirSenha(token, senha);
        if (!sucesso) {
            model.addAttribute("erro", "Token inválido ou expirado.");
            return "redefinir-senha";

        }
        return "redirect:/login?senhaRedefinida";
    }
}
