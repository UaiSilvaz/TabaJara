package br.com.projetotabajara.tabajara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetotabajara.tabajara.entity.Produto;

//extends é a herança, ou seja, a interface ProdutoRepository herda os metodos da interface JpaRepository

// Interface de acesso a dados, onde ficam os metodos de consulta, inserção, atualização e exclusão
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
