package br.com.projetotabajara.tabajara.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetotabajara.tabajara.entity.ItemDoPedido;
import br.com.projetotabajara.tabajara.entity.Pedido;
import br.com.projetotabajara.tabajara.entity.Produto;
import br.com.projetotabajara.tabajara.repository.PedidoRepository;
import br.com.projetotabajara.tabajara.repository.ProdutoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para salvar pedido
    public Pedido salvarpedido(Pedido pedido) {
        pedido.setdataPedido(LocalDate.now());

        for (ItemDoPedido item : pedido.getItens()) {

            Produto produto = produtoRepository.findById(item.getProduto().getIdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            item.setProduto(produto);

            item.setPreco(produto.getValorProduto());

            item.atualizarSubtotal();

            item.setPedido(pedido);

        }

        pedido.atualizarTotal();
        return pedidoRepository.save(pedido);
    }
}
