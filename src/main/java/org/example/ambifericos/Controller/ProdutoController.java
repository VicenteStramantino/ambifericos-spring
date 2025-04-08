package org.example.ambifericos.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.ItemPedidoRequest;
import org.example.ambifericos.DTO.ProdutoRequest;
import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> buscarPorID(@RequestParam Long id){
        Produto produto = produtoService.buscarPorID(id);
        if(produto != null){
            return ResponseEntity.ok(produtoService.buscarPorID(id));
        }
        else{
            return ResponseEntity.internalServerError().body("Erro, produto não encontrado.");
        }
    }

    @Operation(summary = "Adiciona um novo produto", description = "Cria um novo produto")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto do produto a ser criado",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProdutoRequest.class)
            )
    )
    @PostMapping("/inserir")
    public ResponseEntity<?> inserir(@RequestBody ProdutoRequest produtoRequest){
        if(produtoService.inserir(produtoRequest)){
            return ResponseEntity.ok("produto inserido com sucesso");
        }
        else {
            return ResponseEntity.internalServerError().body("Erro, produto ja presente no banco");
        }
    }

    @DeleteMapping("/remover")
    public ResponseEntity<?> remover(@RequestParam Long id){
        if(produtoService.remover(id)){
            return ResponseEntity.ok("Produto removido com sucesso");
        }
        else{
            return ResponseEntity.internalServerError().body("Produto não encontrado.");
        }
    }

}
