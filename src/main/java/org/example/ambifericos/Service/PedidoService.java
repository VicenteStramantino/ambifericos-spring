package org.example.ambifericos.Service;

import org.example.ambifericos.DTO.PedidoRequest;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Model.ItemPedido;
import org.example.ambifericos.Model.Pedido;
import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final  ClienteService  clienteService;

    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService, ProdutoService produtoService){
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public Pedido listaPedidoPeloId(Long id){
        Optional<Pedido> pedido =  pedidoRepository.findById(id);
        return pedido.orElse(null);
    }

    public List<Pedido> listaPedidosPeloCliente(Long id){
        return pedidoRepository.findPedidosByClienteId(id);
    }

    public Pedido converterParaPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.listarClientePeloId(pedidoRequest.getCliente());
        pedido.setCliente(cliente);

        List<ItemPedido> itens = new ArrayList<>();
        for (PedidoRequest.ItemPedidoDTO itemDTO : pedidoRequest.getItens()) {
            Produto produto = produtoService.buscarPorID(itemDTO.getProduto().getId());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setSubtotal(produto.getPreco().multiply(java.math.BigDecimal.valueOf(itemDTO.getQuantidade())));
            itemPedido.setPedido(pedido);

            itens.add(itemPedido);
        }

        pedido.setItens(itens);
        return pedido;
    }

    public int adicionaPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = converterParaPedido(pedidoRequest);

        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            return -1;
        }

        Cliente cliente = clienteService.listarClientePeloId(pedido.getCliente().getId());
        if (cliente == null) {
            return -1;
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            return -3;
        }

        BigDecimal total = BigDecimal.ZERO;
        List<ItemPedido> itensValidos = new ArrayList<>();

        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto() == null || item.getProduto().getId() == null) {
                continue;
            }

            Produto produto = produtoService.buscarPorID(item.getProduto().getId());
            if (produto == null) {
                continue;
            }

            if (produto.getEstoque() < item.getQuantidade()) {
                return -4;
            }

            produto.setEstoque(produto.getEstoque() - item.getQuantidade());

            item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
            item.setProduto(produto);
            item.setPedido(pedido);

            total = total.add(item.getSubtotal());
            itensValidos.add(item);
        }

        if (itensValidos.isEmpty()) {
            return -2;
        }

        pedido.setCliente(cliente);
        pedido.setCreatedAt(LocalDateTime.now());
        pedido.setTotal(total);
        pedido.setItens(itensValidos);
        pedido.setStatus("Pendente");

        pedidoRepository.save(pedido);

        atualizaEstoque(itensValidos);

        return 1;
    }

    public void atualizaEstoque(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            produtoService.atualizarProduto(item.getProduto());
        }
    }


    public boolean removePedido(Long id){
        pedidoRepository.deleteById(id);
        return listaPedidoPeloId(id) == null;
    }
}
