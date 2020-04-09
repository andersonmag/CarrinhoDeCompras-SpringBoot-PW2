package com.example.minhaloja.repositorios;

import com.example.minhaloja.modelo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRole extends JpaRepository<Role, Long> {}