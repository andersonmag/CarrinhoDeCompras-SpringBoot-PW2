package com.example.minhaloja.repositorios;

import javax.transaction.Transactional;
import com.example.minhaloja.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RepositorioCliente extends JpaRepository<Cliente, Long> {

    Iterable<Cliente> findByNomeContaining(String q);
    Cliente findByEmail(String email);
}