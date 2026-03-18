package br.com.projetotabajara.tabajara.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// O metodo contrutor toda vez que for criado um novo fornecedor, assim o
// sistema
// cria um espaço vazio na memoria => Contrutor vazio
@Entity
// Criar uma tabela no banco de dados com o nome Fornecedor (entidade)
// classe de modelagem

public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idFornecedor;

    @Column(nullable = false, length = 40)

    private String nomeFornecedor;

    private String cpjFornecedor;

    @Column(nullable = false, length = 13)

    private String telefoneFornecedor;

    @Column(nullable = false, length = 50)

    private String enderecoFornecedor;

    @Column(nullable = false, length = 40)

    private String cidadeFornecedor;

    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos;

}
