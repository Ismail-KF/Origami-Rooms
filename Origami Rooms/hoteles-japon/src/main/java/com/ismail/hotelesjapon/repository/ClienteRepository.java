package com.ismail.hotelesjapon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Cliente;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByTelefono(String telefono);

    Optional<Cliente> findByPasaporte(String pasaporte);
}