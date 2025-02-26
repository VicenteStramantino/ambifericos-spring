package org.example.ambifericos.Repository;

import org.example.ambifericos.Model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}
