package org.example.ambifericos.Controller;

import org.example.ambifericos.Model.ItemPedido;
import org.example.ambifericos.Service.ItemPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amfirecicos/itemPedido")
@Validated
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService){
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping("/listarTudo")
    public ResponseEntity<?> listaItensPedido(){
        List<ItemPedido> listItemPedido = itemPedidoService.listaItensPedido();
        return listItemPedido.isEmpty()
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

    @PostMapping("/adicionaItemPedido")
    public ResponseEntity<?> adicionaItemPedido(List<ItemPedido> listItemPedido){
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
