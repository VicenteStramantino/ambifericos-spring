package org.example.ambifericos.Controller;

import io.swagger.v3.oas.annotations.media.Content;
import org.example.ambifericos.DTO.AdministradorRequest;
import org.example.ambifericos.DTO.ClienteRequest;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ambifericos/cliente")
@Validated
public class ClienteController {
    public final ClienteService clienteService;
    private final Validator validator;


    public ClienteController(ClienteService clienteService, Validator validator){
        this.clienteService = clienteService;
        this.validator = validator;

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
                : ResponseEntity.internalServerError().body("Não foi possível adicionar o cliente");
    }

    @PutMapping("/atualizaCliente")
    public ResponseEntity<String> atualizarCliente(@RequestBody Cliente clienteAtualizado) {
        try {
            if (clienteAtualizado.getId() == null) {
                return ResponseEntity.badRequest().body("ID do cliente é obrigatório");
            }

            Cliente clienteExistente = clienteService.listarClientePeloId(clienteAtualizado.getId());

            if(clienteExistente == null){
                return ResponseEntity.badRequest().body("Cliente não existe");
            }

            if (clienteAtualizado.getEmail() != null && !clienteAtualizado.getEmail().equals(clienteExistente.getEmail())) {
                return ResponseEntity.badRequest().body("Não é possível atualizar o e-mail do cliente");
            }

            if (clienteAtualizado.getNome() != null) {
                clienteExistente.setNome(clienteAtualizado.getNome());
            }
            if (clienteAtualizado.getSenha() != null) {
                clienteExistente.setSenha(clienteAtualizado.getSenha());
            }
            if (clienteAtualizado.getEndereco() != null) {
                clienteExistente.setEndereco(clienteAtualizado.getEndereco());
            }

            DataBinder dataBinder = new DataBinder(clienteExistente);
            dataBinder.setValidator(validator);
            dataBinder.validate();
            BindingResult result = dataBinder.getBindingResult();
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(retornaErro(result));
            }

            clienteService.atualizaCliente(clienteExistente);
            return ResponseEntity.ok("Cliente atualizado com sucesso");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
    }
    public String retornaErro(BindingResult result) {
        StringBuilder stringBuilderErro = new StringBuilder();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                stringBuilderErro.append("Erro: ").append(error.getDefaultMessage()).append("  |  ");
            }
        }
        return stringBuilderErro.toString();
    }

    @DeleteMapping("/removeCliente")
    public ResponseEntity<?> removeCliente(@RequestParam Long id){
        return clienteService.removeCliente(id)
                ? ResponseEntity.ok("Cliente foi excluído com sucesso!")
                : ResponseEntity.ok("Não foi possível excluído o cliente");
    }
}
