package org.example.ambifericos.Service;


import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    public final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscarTodos(){
        return  produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorID(Long id){
        return produtoRepository.findById(id);
    }


    public boolean inserir(Produto produto){
        if(buscarPorID(produto.getId()).isEmpty()){
            produtoRepository.save((produto));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean remover(Long id){
        if(buscarPorID(id).isEmpty()){
            return false;
        }
        else{
            produtoRepository.deleteById(id);
            return true;
        }
    }
}
