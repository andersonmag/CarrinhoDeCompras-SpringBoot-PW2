package com.example.minhaloja.modelo;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @ManyToOne
    // private Cliente cliente;
    
    @ManyToOne
    private Item item;
    
    private String data;
    private Double valor;

    public Pedido(){}
    public Pedido(Cliente cliente, List<Item> itens, String data, Double valor) {
        // this.cliente = cliente;
        // this.itens = itens;
        this.data = data;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // public Cliente getCliente() {
    //     return cliente;
    // }

    // public void setCliente(Cliente cliente) {
    //     this.cliente = cliente;
    // }

    // public List<Item> getItens() {
    //     return itens;
    // }

    // public void setItens(List<Item> itens) {
    //     this.itens = itens;
    // }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    

    
}