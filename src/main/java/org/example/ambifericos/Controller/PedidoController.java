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
    public ResponseEntity<List<Pedido>> listarPedidos(){
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/listarPedidoPeloId")
    public ResponseEntity<Pedido> listarPedidoPeloId(@RequestParam Long id){
        return ResponseEntity.ok(pedidoService.listaPedidoPeloId(id));
    }

    @GetMapping("/listarPedidosPeloCliente")
    public ResponseEntity<List<Pedido>> listarPedidosPeloCliente(@RequestParam Long id){
        return ResponseEntity.ok(pedidoService.listaPedidosPeloCliente(id));
    }

}
