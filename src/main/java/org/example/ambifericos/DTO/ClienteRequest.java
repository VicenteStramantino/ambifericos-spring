package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para criação de um Cliente")
public class ClienteRequest {

    @Schema(description = "Nome do do cliente", example = "Diego")
    @Column(nullable = false, length = 100)
    private String nome;


    @Schema(description = "E-mail do cliente", example = "diegoseila@gmail.com")
    @Column(nullable = false, unique = true, length = 100)
    private String email;


    @Schema(description = "Senha do do cliente", example = "1234")
    @Column(length = 50)
    private String senha;


    @Schema(description = "Endereco do cliente", example = "Rua germinare")
    @Column(columnDefinition = "TEXT")
    private String endereco;

}
