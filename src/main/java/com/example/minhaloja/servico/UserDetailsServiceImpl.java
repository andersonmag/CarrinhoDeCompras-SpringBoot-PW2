package com.example.minhaloja.servico;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = repositorioCliente.findByEmail(email);

        if (cliente == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return cliente;
    }
}