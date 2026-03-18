package br.com.projetotabajara.tabajara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetotabajara.tabajara.entity.Fornecedor;
import br.com.projetotabajara.tabajara.repository.FornecedorRepository;

@Service
public class FornecedorService {

    // injeção de dependência do repositório
    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Método para salvar fornecedor
    public Fornecedor save(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor findById(Integer id) {
        return fornecedorRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        fornecedorRepository.deleteById(id);
    }

}
