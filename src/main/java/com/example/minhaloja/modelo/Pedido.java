package com.example.minhaloja.modelo;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Escolha um cliente")
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    @NotNull(message = "Escolha algum item")
    @ManyToMany
    private List<Item> itens;

    @NotBlank(message = "Insira a Data")
    private String data;

    @NotNull(message = "O valor não pode ser vazio.")
    @DecimalMin("0.1")
    private Double valor;

    public Pedido() {
    }

    public Pedido(Cliente cliente, List<Item> itens, String data, Double valor) {
        this.cliente = cliente;
        this.itens = itens;
        this.data = data;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Double getItensValores() {

        Double valorTotal = 0.0;

        for (Item item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal;
    }

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
        this.valor = getItensValores();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}