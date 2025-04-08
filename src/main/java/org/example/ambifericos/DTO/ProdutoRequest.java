package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para criação de um Administrador")
public class ProdutoRequest {

    @Schema(description = "Nome do produto", example = "Diego")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Produto super legal")
    private String descricao;

    @Schema(description = "Preço do produto", example = "39.99")
    private BigDecimal preco;

    @Schema(description = "Estoque do produto", example = "10")
    private int estoque;

    @Schema(description = "URL da imagem", example = "https://img.freepik.com/fotos-gratis/imagem-vertical-de-foco-raso-de-um-filhote-de-cachorro-golden-retriever-fofo-sentado-em-um-gramado_181624-27259.jpg?w=360")
    private String imagem;
}
