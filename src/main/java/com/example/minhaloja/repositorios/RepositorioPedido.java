package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Long> {

    Iterable<Pedido> findByClienteNomeContaining(String q);
}