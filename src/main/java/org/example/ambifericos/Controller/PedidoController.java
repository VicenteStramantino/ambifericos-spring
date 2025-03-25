package org.example.ambifericos.Controller;

import org.example.ambifericos.Model.Pedido;
import org.example.ambifericos.Service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

}
