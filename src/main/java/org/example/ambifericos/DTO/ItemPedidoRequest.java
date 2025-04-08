package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para criação de um Item Pedido")
public class ItemPedidoRequest {

    @Schema(description = "ID do Pedido", example = "1")
    private long pedidoId;

    @Schema(description = "ID do Produto", example = "2")
    private long produtoId;

    @Schema(description = "ID do Pedido", example = "10")
    private int quantidade;

    @Schema(description = "Subtotal", example = "20")
    private BigDecimal subtotal;
}
