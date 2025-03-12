package org.example.ambifericos.Controller;

import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambifericos/cliente")
@Validated
public class ClienteController {
    public final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/listarTudo")
    public ResponseEntity<List<Cliente>> listarTudo(){
        return ResponseEntity.ok(clienteService.listarCliente());
    }

    @GetMapping("/listarPeloId")
    public ResponseEntity<Cliente> listarPeloId(@RequestParam Long id){
        return ResponseEntity.ok(clienteService.listarClientePeloId(id));
    }

    @GetMapping("/listarClientePeloEmailSenha")
    public ResponseEntity<Cliente> listarClientePeloEmailSenha(@RequestParam String email, @RequestParam String senha){
        return ResponseEntity.ok(clienteService.listarClientePeloEmailSenha(email, senha));
    }

    @PostMapping("/adicionaCliente")
    public ResponseEntity<?> adicionaCliente(@RequestBody Cliente cliente){
        return clienteService.adicionaCliente(cliente)
                ? ResponseEntity.ok("Cliente adicionado com sucesso!")
                : ResponseEntity.ok("Não foi possível adicionar o cliente");
    }

    @DeleteMapping("/removeCliente")
    public ResponseEntity<?> removeCliente(@RequestParam Long id){
        return clienteService.removeCliente(id)
                ? ResponseEntity.ok("Cliente foi excluído com sucesso!")
                : ResponseEntity.ok("Não foi possível excluído o cliente");
    }
}
