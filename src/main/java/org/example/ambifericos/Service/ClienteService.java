package org.example.ambifericos.Service;

import org.example.ambifericos.DTO.ClienteRequest;
import org.example.ambifericos.DTO.ProdutoRequest;
import org.example.ambifericos.Model.Cliente;
import org.example.ambifericos.Model.Produto;
import org.example.ambifericos.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarCliente(){
        return clienteRepository.findAll();
    }

    public Cliente listarClientePeloId(Long id){
        Optional<Cliente> cliente =  clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    public Cliente listarClientePeloEmailSenha(String email, String senha){
        return clienteRepository.findClienteByEmailAndSenhaIgnoreCase(email, senha);
    }


    public Cliente converteParaCliente(ClienteRequest produtoRequest) {

        Cliente cliente = new Cliente();
        cliente.setNome(produtoRequest.getNome());
        cliente.setSenha(produtoRequest.getSenha());
        cliente.setEmail(produtoRequest.getEmail());
        cliente.setEndereco(produtoRequest.getEndereco());
        return cliente;
    }

    public boolean adicionaCliente(ClienteRequest clienteRequest){
        Cliente cliente = converteParaCliente(clienteRequest);
        if (clienteRepository.existsByNome(cliente.getNome())) {
            return false;
        }
        clienteRepository.save((cliente));
        return true;
    }

    public boolean atualizaCliente(Cliente cliente){
        if (cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
            clienteRepository.save(cliente);
            return true;
        }
        return false;
    }

    public boolean removeCliente(Long id){
        clienteRepository.deleteById(id);
        return listarClientePeloId(id) == null;
    }
}
