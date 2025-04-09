package org.example.ambifericos.Service;
import org.example.ambifericos.DTO.ItemPedidoRequest;
import org.example.ambifericos.DTO.ProdutoRequest;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Model.ItemPedido;
import org.example.ambifericos.Model.Pedido;
import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public boolean inserir(ProdutoRequest produtoRequest){
        Produto produto = converteParaProduto(produtoRequest);
        if (produtoRepository.existsByNome(produto.getNome())) {
            return false;
        }
        produtoRepository.save((produto));
        return true;
    }

    public Produto converteParaProduto(ProdutoRequest produtoRequest) {
        Produto produto = new Produto();

        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setPreco(produtoRequest.getPreco());
        produto.setEstoque(produtoRequest.getEstoque());
        produto.setImagem(produtoRequest.getImagem());

        return produto;
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
