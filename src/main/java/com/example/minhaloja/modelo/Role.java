package com.example.minhaloja.modelo;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private Long id;
    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<Cliente> clientes;

    @Override
    public String getAuthority() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}