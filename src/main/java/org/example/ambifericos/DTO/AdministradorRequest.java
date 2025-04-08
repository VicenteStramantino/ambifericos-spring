package org.example.ambifericos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto para criação de um Administrador")
public class AdministradorRequest {

    @Schema(description = "Nome do administrador", example = "Diego")
    private String nome;

    @Schema(description = "E-mail do administrador", example = "diego@gmail.com")
    private String email;

    @Schema(description = "Senha do administrador", example = "SenhaSuperSecreta")
    private String senha;

}
