package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para criação de um Pedido")
public class PedidoRequest {

    @Schema(description = "ID do cliente", example = "1")
    private long cliente;

    @Schema(description = "Lista de itens do pedido")
    private List<ItemPedidoDTO> itens;

    @Getter
    @Setter
    public static class ItemPedidoDTO {
        @Schema(description = "Produto do pedido", example = "1")
        private Long produtoId;

        @Schema(description = "Quantidade do produto", example = "2")
        private int quantidade;
    }
}
