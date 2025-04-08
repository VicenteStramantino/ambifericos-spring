package org.example.ambifericos.Repository;

import org.example.ambifericos.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidosByClienteId(Long id);
}
