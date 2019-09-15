package com.example.minhaloja.controle;

import java.util.Optional;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorCliente {

    @Autowired
    RepositorioCliente repositorioCliente;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView retorno = new ModelAndView("index.html");
        return retorno;
    }

    @RequestMapping("/formulario_cliente")
    public ModelAndView formularioCliente(Cliente cliente) {
        ModelAndView retorno = new ModelAndView("cadastroCliente.html");
        return retorno;
    }

    @RequestMapping("/novo_cliente")
    public ModelAndView cadastroCliente(@Valid Cliente cliente, BindingResult bidingResult,
            RedirectAttributes redirect) {
        ModelAndView retorno;
        if (bidingResult.hasErrors()) {
            redirect.addFlashAttribute("cliente", cliente);
            retorno = new ModelAndView("cadastroCliente.html");
            return retorno;
        }
        retorno = new ModelAndView("redirect:/");
        repositorioCliente.save(cliente);
        redirect.addFlashAttribute("mensagem", cliente.getNome() + " cadastrado com sucesso!");
        return retorno;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Optional<Cliente> opcao = repositorioCliente.findById(id);
        ModelAndView model = new ModelAndView("redirect:/listar_clientes");
        if (opcao.isPresent()) {
            Cliente cliente = opcao.get();
            repositorioCliente.deleteById(cliente.getId());
            redirect.addFlashAttribute("mensagem", "Cliente excluido com sucesso!");

            return model;
        }
        return model;

    }

    @RequestMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable("id") long id) {
        ModelAndView model = new ModelAndView("cadastroCliente.html");
        Optional<Cliente> opcao = repositorioCliente.findById(id);
        if (opcao.isPresent()) {
            Cliente cliente = opcao.get();
            model.addObject("cliente", cliente);
            return model;
        }

        return model;
    }

    @RequestMapping("/listar_clientes")
    public ModelAndView att() {
        ModelAndView model = new ModelAndView("listar_clientes.html");
        Iterable<Cliente> clientes = repositorioCliente.findAll();
        model.addObject("clientes", clientes);
        return model;
    }

    @RequestMapping("/buscar_cliente")
    public ModelAndView buscar(String q) {
        ModelAndView model = new ModelAndView("listar_clientes.html");
        Iterable<Cliente> clientes = repositorioCliente.findByNomeContaining(q);
        model.addObject("clientes", clientes);

        return model;
    }

}