package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarrinhoRequest {

    @Schema(description = "ID do carrinho existente", example = "5")
    private Long clienteId;

    @Schema(description = "ID do produto", example = "1")
    private Long produtoId;

    @Schema(description = "Quantidade do produto", example = "2")
    private int quantidade;
}
