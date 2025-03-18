package org.example.ambifericos.Service;

import org.example.ambifericos.Model.Pedido;
import org.example.ambifericos.Repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public Pedido listaPedidoPeloId(Long id){
        return pedidoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listaPedidosPeloCliente(Long id){
        return pedidoRepository.findPedidosByClienteId(id);
    }
}
