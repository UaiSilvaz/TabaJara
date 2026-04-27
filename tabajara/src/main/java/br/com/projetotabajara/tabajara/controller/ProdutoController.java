package br.com.projetotabajara.tabajara.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projetotabajara.tabajara.entity.Fornecedor;
import br.com.projetotabajara.tabajara.entity.Produto;
import br.com.projetotabajara.tabajara.service.FornecedorService;
import br.com.projetotabajara.tabajara.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService serviceProduto;

    @Autowired
    private FornecedorService serviceFornecedor;

    @GetMapping({ "", "/listar" })
    public String listar(Model model) {
        List<Produto> produtos = serviceProduto.findAll();
        model.addAttribute("produtos", produtos);
        return "produto/listarProdutos";
    }

    @GetMapping({ "/novo", "/criar" })
    public String criarForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("fornecedores", serviceFornecedor.findAll());
        return "produto/formularioProduto";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, Integer idFornecedor) {
        if (idFornecedor != null) {
            Fornecedor fornecedor = serviceFornecedor.findById(idFornecedor);
            produto.setFornecedor(fornecedor);
        }

        serviceProduto.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Produto produto = serviceProduto.findById(id);
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", serviceFornecedor.findAll());
        model.addAttribute("idFornecedor",
                produto.getFornecedor() != null ? produto.getFornecedor().getIdFornecedor() : null);
        return "produto/formularioProduto";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute Produto produto, Integer idFornecedor) {
        produto.setIdProduto(id);

        if (idFornecedor != null) {
            Fornecedor fornecedor = serviceFornecedor.findById(idFornecedor);
            produto.setFornecedor(fornecedor);
        }

        serviceProduto.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirPorGet(@PathVariable Integer id) {
        serviceProduto.deleteById(id);
        return "redirect:/produtos";
    }

    @PostMapping("/excluir/{id}")
    public String excluirPorPost(@PathVariable Integer id) {
        serviceProduto.deleteById(id);
        return "redirect:/produtos";
    }
}
