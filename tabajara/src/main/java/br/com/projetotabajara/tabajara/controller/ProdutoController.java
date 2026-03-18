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
    // Injeção de dependência do serviço de produtos

    @Autowired
    private ProdutoService serviceProduto;
    // Método para listar todos os produtos e exibir na página de lista de produtos

    @Autowired
    private FornecedorService serviceFornecedor;

    @GetMapping("/listar")

    public String listar(Model model) {

        // Busca todos os produtos

        List<Produto> produtos = serviceProduto.findAll();

        // Adiciona os produtos ao modelo

        model.addAttribute("produtos", produtos);

        // Retorna a página de lista de produtos

        return "produto/listarProdutos";

    }

    // Método para abrir o formulário de criação de produtos

    @GetMapping("/criar")

    public String criarForm(Model model) {

        // Adiciona um novo produto ao modelo

        model.addAttribute("produto", new Produto());

        // Busca todos os fornecedores
        List<Fornecedor> fornecedores = serviceFornecedor.findAll();
        // Adiciona os fornecedores ao modelo
        model.addAttribute("fornecedores", fornecedores);
        // Retorna a página do formulário de produtos
        return "produto/formularioProduto";

    }

    // Método para salvar um produto com POST

    @PostMapping("/salvar")

    public String salvar(@ModelAttribute Produto produto, Integer idFornecedor) {

        if (idFornecedor != null) {
            Fornecedor fornecedor = serviceFornecedor.findById(idFornecedor);
            produto.setFornecedor(fornecedor);
        }

        // Salva o produto

        serviceProduto.save(produto);

        // Redireciona para a lista de produtos

        return "redirect:/produtos/listar";

    }

    // Método para abrir o formulário de edição de produto

    @GetMapping("/editar/{id}")

    public String editarForm(@PathVariable Integer id, Model model) {

        // Busca o produto pelo id

        Produto produto = serviceProduto.findById(id);

        // Adiciona o produto ao modelo

        model.addAttribute("produto", produto);

        // Busca todos os fornecedores
        List<Fornecedor> fornecedores = serviceFornecedor.findAll();
        model.addAttribute("fornecedores", fornecedores);

        // Adiciona o id do fornecedor para o select
        model.addAttribute("idFornecedor",
                produto.getFornecedor() != null ? produto.getFornecedor().getIdFornecedor() : null);

        // Retorna a página do formulário de produtos
        return "produto/formularioProduto";

    }

    // Método para atualizar um produto com POST

    @PostMapping("/atualizar/{id}")

    public String atualizar(@PathVariable Integer id, @ModelAttribute Produto produto, Integer idFornecedor) {

        // Define o id do produto

        produto.setIdProduto(id);

        if (idFornecedor != null) {
            Fornecedor fornecedor = serviceFornecedor.findById(idFornecedor);
            produto.setFornecedor(fornecedor);
        }

        // Salva o produto

        serviceProduto.save(produto);

        // Redireciona para a lista de produtos

        return "redirect:/produtos/listar";

    }

    // Método para excluir um produto pelo id

    @GetMapping("/excluir/{id}")

    public String excluir(@PathVariable Integer id) {

        serviceProduto.deleteById(id); // Exclui o produto pelo id

        return "redirect:/produtos/listar"; // Redireciona para a lista de produtos

    }

}
