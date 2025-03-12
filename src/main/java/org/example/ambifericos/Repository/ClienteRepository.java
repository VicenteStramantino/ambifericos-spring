package org.example.ambifericos.Repository;

import org.example.ambifericos.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findClienteByEmailAndSenhaIgnoreCase(String email, String senha);
}
