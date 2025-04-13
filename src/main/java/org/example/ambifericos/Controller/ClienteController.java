package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.ClienteRequest;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Service.ClienteService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> listarPeloId(@RequestParam Long id){
        Cliente cliente = clienteService.listarClientePeloId(id);
        return  cliente != null
            ? ResponseEntity.ok(cliente)
            : ResponseEntity.internalServerError().body("Cliente não foi encontrado");
    }

    @GetMapping("/listarClientePeloEmailSenha")
    public ResponseEntity<?> listarClientePeloEmailSenha(@RequestParam String email, @RequestParam String senha){
        Cliente cliente = clienteService.listarClientePeloEmailSenha(email, senha);
        return  cliente != null
                ? ResponseEntity.ok(cliente)
                : ResponseEntity.internalServerError().body("Cliente não foi encontrado");
    }

    @PostMapping("/adicionaCliente")
    @Operation(summary = "Adiciona um novo Cliente", description = "Cria um novo cliente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto do cliente a ser criado",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClienteRequest.class)
            )
    )
    public ResponseEntity<?> adicionaCliente(@RequestBody ClienteRequest clienteRequest){
        return clienteService.adicionaCliente(clienteRequest)
                ? ResponseEntity.ok("Cliente adicionado com sucesso!")
                : ResponseEntity.internalServerError().body("Não foi possível adicionar o cliente, pois o mesmo já está cadastrado!");
    }

    @PutMapping("/atualizaCliente")
    public ResponseEntity<String> atualizarCliente(@RequestBody Cliente clienteAtualizado) {
        try {
            if (clienteAtualizado.getId() == null) {
                return ResponseEntity.badRequest().body("ID do cliente é obrigatório");
            }

            Cliente clienteNovo = clienteService.listarClientePeloId(clienteAtualizado.getId());

            if(clienteNovo == null){
                return ResponseEntity.badRequest().body("Cliente não existe");
            }

            if (clienteAtualizado.getEmail() != null && !clienteAtualizado.getEmail().equals(clienteNovo.getEmail())) {
                return ResponseEntity.badRequest().body("Não é possível atualizar o e-mail do cliente");
            }

            if (clienteAtualizado.getNome() != null) {
                clienteNovo.setNome(clienteAtualizado.getNome());
            }
            if (clienteAtualizado.getSenha() != null) {
                clienteNovo.setSenha(clienteAtualizado.getSenha());
            }
            if (clienteAtualizado.getEndereco() != null) {
                clienteNovo.setEndereco(clienteAtualizado.getEndereco());
            }
            if (clienteNovo.isAdm() != clienteAtualizado.isAdm()) {
                clienteNovo.setAdm(clienteAtualizado.isAdm());
            }

            return clienteService.atualizaCliente(clienteNovo)
                    ? ResponseEntity.ok("Cliente atualizado com sucesso")
                    : ResponseEntity.internalServerError().body("Cliente atualizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
    }

    @DeleteMapping("/removeCliente")
    public ResponseEntity<?> removeCliente(@RequestParam Long id){
        return clienteService.removeCliente(id)
                ? ResponseEntity.ok("Cliente foi excluído com sucesso!")
                : ResponseEntity.ok("Não foi possível excluído o cliente");
    }
}
