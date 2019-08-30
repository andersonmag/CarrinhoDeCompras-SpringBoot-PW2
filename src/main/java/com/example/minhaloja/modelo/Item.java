package com.example.minhaloja.modelo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 2, max = 50, message = "O nome precisa ter entre {min} e {max} catacteres.")
    private String nome;
    
    @NotNull(message = "O preço não pode ser vazio.")
    @DecimalMin("0.1") 
    private double preco;

    // @OneToMany(mappedBy = "item")
    // private List<Pedido> pedidos;

    public Item(){
        super();
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    
}