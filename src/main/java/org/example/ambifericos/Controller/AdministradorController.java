package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.AdministradorRequest;
import org.example.ambifericos.DTO.PedidoRequest;
import org.example.ambifericos.Model.Administrador;
import org.example.ambifericos.Service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/ambifericos/adminstrador")
@RestController
@Validated
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService){
        this.administradorService = administradorService;
    }

    @GetMapping("/selecionarTodos")
    public ResponseEntity<?> buscarAdminstradores(){
        return ResponseEntity.ok(administradorService.buscarTodos());
    }

    @GetMapping("/selecionarPorId")
    public ResponseEntity<?> selecionarPorID(@RequestParam Long id){
        if(administradorService.buscarPorID(id).isEmpty()){
            return ResponseEntity.internalServerError().body("Adminstrador não encontrado.");
        }
        else{
            return ResponseEntity.ok(administradorService.buscarPorID(id));
        }
    }

    @Operation(summary = "Adiciona um novo administrador", description = "Cria um novo administrador")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto do pedido a ser criado",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AdministradorRequest.class)
            )
    )
    @PostMapping("/inserir")
    public ResponseEntity<?> inserir(@RequestBody Administrador administrador){
        if(administradorService.inserirAdminstrador(administrador)){
            return ResponseEntity.ok("adminstrador inserido com sucesso.");
        }
        else{
            return ResponseEntity.internalServerError().body("Erro, adminstador com este id ja existente.");
        }
    }



    @DeleteMapping("/remover")
    public ResponseEntity<?> remover(@RequestParam Long id){
        if(!administradorService.deletarAdmin(id)){
            return ResponseEntity.internalServerError().body("Erro, adminstrador não encontrado");
        }
        else{
            return ResponseEntity.ok("Adminstrador deletado");
        }
    }
}
