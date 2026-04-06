package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Método para criar um novo usuario e emcaminhar para o formulario de cadastro

    @GetMapping("/criar")
    public String criarForm(Model model) {

        // Adiciona um novo usuario ao modelo

        model.addAttribute("usuario", new Usuario());

        // Retorna a página do formulário de cadastro de usuario

        return "usuario/formularioUsuario";

    }

    @PostMapping("/salvar")
    public String salvar(Usuario usuario, Model model) {
        try {
            usuarioService.save(usuario);
            return "redirect:/login?cadastroSucesso";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erroCadastro", ex.getMessage());
            return "usuario/formularioUsuario";
        }
    }

}
