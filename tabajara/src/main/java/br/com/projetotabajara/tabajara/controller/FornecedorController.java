package br.com.projetotabajara.tabajara.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projetotabajara.tabajara.entity.Fornecedor;
import br.com.projetotabajara.tabajara.service.FornecedorService;

@Controller
@RequestMapping({ "/fornecedores", "/fornecedor" })
public class FornecedorController {

    @Autowired
    private FornecedorService serviceFornecedor;

    @GetMapping({ "", "/listar", "/listarFornecedores" })
    public String listarFornecedores(Model model) {
        List<Fornecedor> fornecedores = serviceFornecedor.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "fornecedor/listarFornecedores";
    }

    @GetMapping({ "/novo", "/criar", "/criarFornecedores" })
    public String criarForm(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor/formularioFornecedor";
    }

    @PostMapping({ "/salvar", "/salvarFornecedores" })
    public String salvar(@ModelAttribute Fornecedor fornecedor, BindingResult result) {
        if (result.hasErrors()) {
            return "fornecedor/formularioFornecedor";
        }

        serviceFornecedor.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Fornecedor fornecedor = serviceFornecedor.findById(id);
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedor/formularioFornecedor";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute Fornecedor fornecedor) {
        fornecedor.setIdFornecedor(id);
        serviceFornecedor.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/excluir/{id}")
    public String excluirPorGet(@PathVariable Integer id) {
        serviceFornecedor.deleteById(id);
        return "redirect:/fornecedores";
    }

    @PostMapping("/excluir/{id}")
    public String excluirPorPost(@PathVariable Integer id) {
        serviceFornecedor.deleteById(id);
        return "redirect:/fornecedores";
    }
}
