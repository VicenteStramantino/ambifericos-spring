package org.example.ambifericos.Service;

import org.example.ambifericos.DTO.CarrinhoRequest;
import org.example.ambifericos.Model.Carrinho;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Repository.CarrinhoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ClienteService clienteService, ProdutoService produtoService) {
        this.carrinhoRepository = carrinhoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public List<Carrinho> listarCarrinhoPorCliente(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId);
    }

    public int adicionarItem(CarrinhoRequest carrinhoRequest) {
        Carrinho carrinho = converterParaCarrinho(carrinhoRequest);

        if (carrinho.getCliente().getId() == null){
            return -1;
        }
        if (carrinho.getProduto().getId() == null) {
            return -2;
        }
        if (carrinho.getQuantidade() <= 0) {
            return -3;
        }

        Optional<Carrinho> carrinhoExistenteOpt = carrinhoRepository.findByClienteIdAndProdutoId(carrinho.getCliente().getId(), carrinho.getProduto().getId());

        Carrinho item;
        int novaQuantidade;

        if (carrinhoExistenteOpt.isPresent()) {
            item = carrinhoExistenteOpt.get();
            novaQuantidade = item.getQuantidade() + carrinho.getQuantidade();
        } else {
            item = new Carrinho();
            item.setCliente(carrinho.getCliente());
            item.setProduto(carrinho.getProduto());
            novaQuantidade = carrinho.getQuantidade();
        }

        if (novaQuantidade > carrinho.getProduto().getEstoque()) {
            return -4;
        }

        item.setQuantidade(novaQuantidade);
        BigDecimal subtotal = carrinho.getProduto().getPreco().multiply(BigDecimal.valueOf(novaQuantidade));
        item.setSubtotal(subtotal);

        carrinhoRepository.save(item);

        return 1;
    }

    public Carrinho converterParaCarrinho(CarrinhoRequest carrinhoRequest){
        Carrinho carrinho = new Carrinho();

        carrinho.setCliente(clienteService.listarClientePeloId(carrinhoRequest.getClienteId()));
        carrinho.setProduto(produtoService.buscarPorID(carrinhoRequest.getProdutoId()));
        carrinho.setQuantidade(carrinhoRequest.getQuantidade());

        return carrinho;
    }

    public boolean removerItem(Long itemId) {
        if (!carrinhoRepository.existsById(itemId)) return false;
        carrinhoRepository.deleteById(itemId);
        return true;
    }
}
