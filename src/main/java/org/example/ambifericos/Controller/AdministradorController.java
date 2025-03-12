package org.example.ambifericos.Controller;

import org.example.ambifericos.Model.Administrador;
import org.example.ambifericos.Service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/inserir")
    public ResponseEntity<?> inserir(@RequestParam Administrador administrador){
        if(administradorService.inserirAdminstrador(administrador) == true){
            return ResponseEntity.ok("adminstrador inserido com sucesso.");
        }
        else{
            return ResponseEntity.internalServerError().body("Erro, adminstador com este id ja existente.");
        }
    }



    @GetMapping("/remover")
    public ResponseEntity<?> remover(@RequestParam Long id){
        if(administradorService.deletarAdmin(id) == false){
            return ResponseEntity.internalServerError().body("Erro, adminstrador não encontrado");
        }
        else{
            return ResponseEntity.ok("Adminstrador deletado");
        }
    }
}
