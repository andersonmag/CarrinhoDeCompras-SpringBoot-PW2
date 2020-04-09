package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLiga extends JpaRepository<Liga, Long> {}