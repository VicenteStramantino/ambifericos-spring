package org.example.ambifericos.Service;
import org.example.ambifericos.Model.Cliente;
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

    public Produto buscarPorID(Long id){
        Optional<Produto> produto =  produtoRepository.findById(id);
        return produto.orElse(null);
    }

    public boolean inserir(Produto produto){
        if(buscarPorID(produto.getId()) == null){
            produtoRepository.save((produto));
            return true;
        }
        else{
            return false;
        }
    }

    public void atualizarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public boolean remover(Long id){
        if(buscarPorID(id) != null){
            produtoRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
