package com.example.minhaloja.modelo;

import java.time.LocalDate;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotNull(message = "Escolha um cliente")
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    @NotEmpty(message = "Escolha algum item")
    @ManyToMany
    private List<Item> itens;

    @JsonFormat(pattern = "dd.mm.yyyy", shape = Shape.STRING)
    private LocalDate data;

    @NotNull(message = "O valor não pode ser vazio.")
    @DecimalMin("0.1")
    private double valor;

    public Pedido() {}

    public Pedido(Cliente cliente, List<Item> itens, LocalDate data, double valor) {
        this.cliente = cliente;
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

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public double getItensValores() {

        double valorTotal = 0.0;

        for (Item item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal;
    }

    public double getValor() {
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
