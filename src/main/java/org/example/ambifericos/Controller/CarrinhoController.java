package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.CarrinhoRequest;
import org.example.ambifericos.DTO.PedidoRequest;
import org.example.ambifericos.Model.Carrinho;
import org.example.ambifericos.Service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambifericos/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/listarPorCliente")
    public ResponseEntity<?> listarPorCliente(@RequestParam Long clienteId) {
        List<Carrinho> itens = carrinhoService.listarCarrinhoPorCliente(clienteId);
        return itens.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(itens);
    }

    @Operation(summary = "Adiciona um novo item no carrinho", description = "Cria um novo item no carrinho do cliente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto do item do carrinho a ser criado",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CarrinhoRequest.class)
            )
    )
    @PostMapping("/adicionarItem")
    public ResponseEntity<?> adicionarItem(@RequestBody CarrinhoRequest carrinhoRequest) {
        int resultado = carrinhoService.adicionarItem(carrinhoRequest);

        return switch (resultado) {
            case -1 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não foi encontrado!");
            case -2 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto válido foi encontrado!");
            case -3 -> ResponseEntity.badRequest().body("Quantidade deve ser válida!");
            case -4 -> ResponseEntity.badRequest().body("Quantidade solicitada excede o estoque do produto!");
            default -> ResponseEntity.ok("Pedido foi adicionado com sucesso!");
        };
    }

    @DeleteMapping("/removerItem")
    public ResponseEntity<?> removerItem(@RequestParam Long itemId) {
        return carrinhoService.removerItem(itemId)
                ? ResponseEntity.ok("Item removido com sucesso!")
                : ResponseEntity.status(404).body("Item não encontrado.");
    }
}
