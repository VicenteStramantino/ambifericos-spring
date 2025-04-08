package org.example.ambifericos.Service;

import org.example.ambifericos.Model.Administrador;
import org.example.ambifericos.Repository.AdministradorRepository;
import org.springframework.expression.spel.ast.OpNE;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdministradorService {

    public final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;

    }


    public List<Administrador> buscarTodos() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> buscarPorID(Long id) {
        return administradorRepository.findById(id);
    }

    public boolean inserirAdminstrador(Administrador administrador) {
        if (administradorRepository.existsByNome(administrador.getNome())) {
            return false;
        }
        administradorRepository.save((administrador));
        return true;

    }

    public boolean deletarAdmin(Long id){
        if(buscarPorID(id).isEmpty()){
            return false;
        }
        administradorRepository.deleteById(id);
        return true;
    }
}
