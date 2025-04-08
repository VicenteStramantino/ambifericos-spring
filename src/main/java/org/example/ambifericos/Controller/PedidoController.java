package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.PedidoRequest;
import org.example.ambifericos.Model.Pedido;
import org.example.ambifericos.Service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambifericos/pedido")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<?> listarPedidos(){
        List<Pedido> listaPedido = pedidoService.listarPedidos();
        return !listaPedido.isEmpty()
                ? ResponseEntity.ok(listaPedido)
                : ResponseEntity.internalServerError().body("Não existe pedidos cadastrados!");
    }

    @GetMapping("/listarPedidoPeloId")
    public ResponseEntity<?> listarPedidoPeloId(@RequestParam Long id){
        Pedido pedido = pedidoService.listaPedidoPeloId(id);
        return pedido != null
                ? ResponseEntity.ok(pedido)
                : ResponseEntity.internalServerError().body("Não existe pedido cadastrado!");
    }

    @GetMapping("/listarPedidosPeloCliente")
    public ResponseEntity<?> listarPedidosPeloCliente(@RequestParam Long id){
        List<Pedido> listaPedido = pedidoService.listaPedidosPeloCliente(id);

        return !listaPedido.isEmpty()
                ? ResponseEntity.ok(listaPedido)
                : ResponseEntity.internalServerError().body("O cliente não possui nenhum pedido!");
    }

    @Operation(summary = "Adiciona um novo pedido", description = "Cria um novo pedido para um cliente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto do pedido a ser criado",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PedidoRequest.class)
            )
    )
    @PostMapping("/adicionaPedido")
    public ResponseEntity<?> adicionaPedido(@RequestBody PedidoRequest pedidoRequest) {
        int resultado = pedidoService.adicionaPedido(pedidoRequest);

        return switch (resultado) {
            case -1 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não foi encontrado!");
            case -2 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto válido foi encontrado no pedido!");
            case -3 -> ResponseEntity.badRequest().body("Pedido deve conter pelo menos um item!");
            default -> ResponseEntity.ok("Pedido foi adicionado com sucesso!");
        };
    }

    @DeleteMapping("/removePedido")
    public ResponseEntity<?> removePedido(@RequestParam Long id){
        return pedidoService.removePedido(id)
                ? ResponseEntity.ok("Pedido foi excluído com sucesso!")
                : ResponseEntity.internalServerError().body("Não foi possível excluído o pedido");
    }
}
