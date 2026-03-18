package br.com.projetotabajara.tabajara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetotabajara.tabajara.entity.Fornecedor;

// Interface de acesso a dados, onde ficam os metodos de consulta, inserção, atualização e exclusão
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

}
