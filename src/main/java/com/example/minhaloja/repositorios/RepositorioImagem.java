package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioImagem extends JpaRepository<Imagem, Long> {}