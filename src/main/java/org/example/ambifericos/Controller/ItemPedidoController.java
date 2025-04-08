package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.ambifericos.DTO.ItemPedidoRequest;
import org.example.ambifericos.DTO.PedidoRequest;
import org.example.ambifericos.Model.ItemPedido;
import org.example.ambifericos.Service.ItemPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambifericos/itemPedido")
@Validated
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService){
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping("/listarTudo")
    public ResponseEntity<?> listaItensPedido(){
        List<ItemPedido> listItemPedido = itemPedidoService.listaItensPedido();
        return !listItemPedido.isEmpty()
                ? ResponseEntity.ok(itemPedidoService.listaItensPedido())
                : ResponseEntity.internalServerError().body("Não existe nenhum item pedido no banco!");
    }

    @GetMapping("/listaItemPedidoId")
    public ResponseEntity<?> listaItemPedidoId(@RequestParam Long id){
        ItemPedido listItemPedido = itemPedidoService.listaItemPedidoPeloId(id);
        return listItemPedido != null
                ? ResponseEntity.ok(itemPedidoService.listaItensPedido())
                : ResponseEntity.internalServerError().body("Não existe nenhum item pedido com esse ID!");
    }

    @Operation(
            summary = "Adiciona novos itens ao pedido",
            description = "Cria uma lista de itens do pedido para um cliente"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Lista de objetos do item do pedido a ser criada",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ItemPedidoRequest.class))
            )
    )
    @PostMapping("/adicionaItemPedido")
    public ResponseEntity<?> adicionaItemPedido(@RequestBody List<ItemPedidoRequest> listItemPedido){
        return itemPedidoService.adicionaItemPedido(listItemPedido)
            ? ResponseEntity.ok("Item pedido foi adicionado com sucesso")
            : ResponseEntity.ok("Não foi possivel adicionar o item pedido");
    }

    @DeleteMapping("removeItemPedido")
    public ResponseEntity<?> removeItemPedido(@RequestParam Long id){
        return itemPedidoService.removeItemPedido(id)
                ? ResponseEntity.ok("Cliente foi excluído com sucesso!")
                : ResponseEntity.ok("Não foi possível excluído o cliente");
    }
}
