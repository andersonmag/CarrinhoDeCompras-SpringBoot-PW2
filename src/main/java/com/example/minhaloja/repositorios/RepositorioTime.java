package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioTime extends JpaRepository<Time, Long>{

    
}