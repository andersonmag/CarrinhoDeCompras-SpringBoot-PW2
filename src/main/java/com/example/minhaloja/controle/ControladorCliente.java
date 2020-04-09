package com.example.minhaloja.controle;

import java.util.Optional;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/cliente")
@RestController
public class ControladorCliente {

    @Autowired
    RepositorioCliente repositorioCliente;

    @GetMapping("/cadastro")
    public ModelAndView formularioCliente() {
        return new ModelAndView("cadastroCliente");
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastroCliente(@Valid Cliente cliente, BindingResult bidingResult) {
        ModelAndView model = new ModelAndView("cadastroCliente");

        if (bidingResult.hasErrors()) {
            model.addObject("cliente", cliente);
            return model;
        }
        model.setViewName("redirect:/");
        repositorioCliente.save(cliente);
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Optional<Cliente> opcao = repositorioCliente.findById(id);
        if (opcao.isPresent()) {
            Cliente cliente = opcao.get();
            repositorioCliente.deleteById(cliente.getId());
            redirect.addFlashAttribute("mensagem", "Cliente excluido com sucesso!");
        }

        return new ModelAndView("redirect:/listar_clientes");
    }

    @RequestMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("cadastroCliente");
        Optional<Cliente> opcao = repositorioCliente.findById(id);

        if (opcao.isPresent()) {
            model.addObject("cliente", opcao.get());
            return model;
        }
        model.setStatus(HttpStatus.NOT_FOUND);
        return model;
    }

    @RequestMapping("/lista")
    public ModelAndView listar() {
        ModelAndView model = new ModelAndView("listar_clientes");
        model.addObject("clientes", repositorioCliente.findAll());
        return model;
    }

    @RequestMapping("/buscar")
    public ModelAndView buscar(String q) {
        ModelAndView model = new ModelAndView("listar_clientes");
        model.addObject("clientes", repositorioCliente.findByNomeContaining(q));
        return model;
    }
}