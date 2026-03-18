package br.com.projetotabajara.tabajara.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// O metodo contrutor toda vez que for criado um novo produto, assim o sistema
// cria um espaço vazio na memoria => Contrutor vazio
@Entity
// Criar uma tabela no banco de dados com o nome Produto (entidade)
// classe de modelagem
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProduto;

    @Column(nullable = false, length = 40)
    private String descProduto;

    private Double valorProduto;

    @Column(nullable = false, length = 10)
    private String unidadeProduto;

    @Column(nullable = false, length = 30)
    private String marcaProduto;

    @ManyToOne
    @JoinColumn(name = "idFornecedor_fk")
    private Fornecedor fornecedor;

    // A chave estrangeira é a coluna que faz a ligação entre as tabelas, nesse caso
    // a coluna idFornecedor_fk é a chave estrangeira que liga a tabela Produto com
    // a tabela Fornecedor, ou seja, cada produto tem um fornecedor (Many-to-One).

}
