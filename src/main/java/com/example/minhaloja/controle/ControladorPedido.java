package com.example.minhaloja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioCliente;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorPedido {

    @Autowired
    RepositorioPedido repositorioPedido;
    @Autowired
    RepositorioCliente repositorioCliente;
    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/fazer_pedido")
    public ModelAndView fazerPedido(RedirectAttributes redirect, Pedido pedido, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("finalizarPedido.html");
        Iterable<Cliente> clientes = repositorioCliente.findAll();
        Iterable<Item> itens = repositorioItem.findAll();
        List<Item> car = (ArrayList) request.getSession().getAttribute("car");

        model.addObject("clientes", clientes);

        if(car == null){
            car = new ArrayList<>();
        }

        if(car.isEmpty()){

            model.addObject("itens", itens);
            return model;

        }

        model.addObject("itens", car);
        return model;
    }

    @RequestMapping("/novo_pedido")
    public ModelAndView finalizarPedido(@Valid Pedido pedido, BindingResult bidingResult,
     RedirectAttributes redirect) {
        ModelAndView model;

        if (bidingResult.hasErrors()) {
            model = new ModelAndView("finalizarPedido.html");
            Iterable<Cliente> clientes = repositorioCliente.findAll();
            Iterable<Item> itens = repositorioItem.findAll();
            redirect.addFlashAttribute("pedido", pedido);
            model.addObject("clientes", clientes);
            model.addObject("itens", itens);

            return model;
        }

        model = new ModelAndView("redirect:/pedidos");
        repositorioPedido.save(pedido);
        redirect.addFlashAttribute("mensagem", "Pedido cadastrado com sucesso!");

        return model;
    }

    @RequestMapping("/pedidos")
    public ModelAndView listar_itens(RedirectAttributes redirect) {
        ModelAndView model = new ModelAndView("listar_pedidos.html");
        Iterable<Pedido> pedidos = repositorioPedido.findAll();
        model.addObject("pedidos", pedidos);

        return model;
    }

    @RequestMapping("/atualizar_pedido/{id}")
    public ModelAndView atualizar(@PathVariable("id") long id) {
        ModelAndView model = new ModelAndView("finalizarPedido.html");
        Optional<Pedido> opcao = repositorioPedido.findById(id);
        if (opcao.isPresent()) {

            Iterable<Cliente> clientes = repositorioCliente.findAll();
            Iterable<Item> itens = repositorioItem.findAll();
            model.addObject("clientes", clientes);
            model.addObject("itens", itens);

            Pedido pedido = opcao.get();
            model.addObject("pedido", pedido);

            return model;

        }

        return model;
    }

    @RequestMapping("/excluir_pedido/{id}")
    public ModelAndView excluir(@PathVariable("id") long id, RedirectAttributes redirect) {
        ModelAndView model = new ModelAndView("redirect:/pedidos");
        Optional<Pedido> opcao = repositorioPedido.findById(id);
        if (opcao.isPresent()) {

            Pedido pedido = opcao.get();
            repositorioPedido.deleteById(pedido.getId());
            redirect.addFlashAttribute("mensagem", "Pedido Excluido com Sucesso!");
            return model;
        }

        return model;
    }

    @RequestMapping("/buscar")
    public ModelAndView buscar(String q) {
        ModelAndView model = new ModelAndView("listar_pedidos.html");
        Iterable<Pedido> pedidos = repositorioPedido.findByClienteNomeContaining(q);
        model.addObject("pedidos", pedidos);

        return model;
    }

}