package org.example.ambifericos.Repository;

import org.example.ambifericos.Model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findAllByProdutoId(Long id); //listaItensProdutoId
}
