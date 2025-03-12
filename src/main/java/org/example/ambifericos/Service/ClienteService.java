package org.example.ambifericos.Service;

import org.example.ambifericos.Model.Cliente;
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
        return clienteRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado"));
    }

    public Cliente listarClientePeloEmailSenha(String email, String senha){
        return clienteRepository.findClienteByEmailAndSenhaIgnoreCase(email, senha);
    }

    public boolean adicionaCliente(Cliente cliente){
        if(listarClientePeloId(cliente.getId()) != null) {
            clienteRepository.save(cliente);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeCliente(Long id){
        clienteRepository.deleteById(id);

        return listarClientePeloId(id) == null;
    }
}
