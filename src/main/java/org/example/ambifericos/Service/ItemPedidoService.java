package org.example.ambifericos.Service;

import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Model.ItemPedido;
import org.example.ambifericos.Repository.ItemPedidoRepository;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository,PedidoService pedidoService ,ProdutoService produtoService){
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
    }

    public List<ItemPedido> listaItensPedido(){
        try {
            return itemPedidoRepository.findAll();
        }catch (Exception npc){
            System.out.println(npc.getMessage());
            return new ArrayList<ItemPedido>();
        }
    }

    public ItemPedido listaItemPedidoPeloId(Long id){
        return itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public boolean adicionaItemPedido(List<ItemPedido> listItemPedido){
        for (ItemPedido itemPedido : listItemPedido){
            if(pedidoService.listaPedidoPeloId(itemPedido.getPedido().getId()) != null && produtoService.buscarPorID(itemPedido.getPedido().getId()) != null){
                itemPedidoRepository.save(itemPedido);
            }else{
                return false;
            }
        }
        return true;
    }

    public boolean removeItemPedido(Long id){
        itemPedidoRepository.deleteById(id);
        return listaItemPedidoPeloId(id) == null;
    }

}
