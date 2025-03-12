package org.example.ambifericos.Controller;


import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ambifericos/produtos")
@RestController
@Validated
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }


    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(){
        return ResponseEntity.ok(produtoService.buscarTodos());
    }


    @GetMapping("/buscarPorID")
    public ResponseEntity<?> buscarPorID(Long id){
        if(produtoService.buscarPorID(id).isPresent()){
            return ResponseEntity.ok(produtoService.buscarPorID(id));
        }
        else{
            return ResponseEntity.internalServerError().body("Erro, produto não encontrado.");
        }
    }


    @GetMapping("/inserir")
    public ResponseEntity<?> inserir(Produto produto){
        if(produtoService.inserir(produto) == true){
            return ResponseEntity.ok("produto inserido com sucesso");
        }
        else {
            return ResponseEntity.internalServerError().body("Erro, produto ja presente no banco");
        }
    }


    @GetMapping("/remover")
    public ResponseEntity<?> remover(Long id){
        if(produtoService.remover(id) == true){
            return ResponseEntity.ok("Produto removido com sucesso");
        }
        else{
            return ResponseEntity.internalServerError().body("Produto não encontrado.");
        }
    }

}
