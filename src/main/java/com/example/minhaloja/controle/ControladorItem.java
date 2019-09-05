package com.example.minhaloja.controle;

import java.util.Optional;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorItem {

    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/formulario_item")
    public ModelAndView formularioItem(Item item) {
        ModelAndView retorno = new ModelAndView("cadastroItens.html");

        return retorno;
    }

    @RequestMapping("/novo_item")
    public ModelAndView cadastroItem(@Valid Item item, BindingResult bidingResult, RedirectAttributes redirect) {
        ModelAndView retorno;
        if (bidingResult.hasErrors()) {
            redirect.addFlashAttribute("item", item);
            retorno = new ModelAndView("cadastroItens.html");
            return retorno;
        }
        retorno = new ModelAndView("redirect:/");
        repositorioItem.save(item);
        redirect.addFlashAttribute("mensagem", "Item cadastrado com sucesso!");
        return retorno;
    }

    @RequestMapping("/listar_itens")
    public ModelAndView att() {
        ModelAndView model = new ModelAndView("listar_itens.html");
        Iterable<Item> itens = repositorioItem.findAll();
        model.addObject("itens", itens);
        return model;
    }

    @RequestMapping("/excluir_cadastro/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Optional<Item> opcao = repositorioItem.findById(id);
        ModelAndView model = new ModelAndView("redirect:/listar_itens");
        if (opcao.isPresent()) {
            Item item = opcao.get();
            repositorioItem.deleteById(item.getId());
            redirect.addFlashAttribute("mensagem", "Item excluido com sucesso!");

            return model;
        }
        return model;

    }

    @RequestMapping("/atualizar_cadastro/{id}")
    public ModelAndView atualizar(@PathVariable("id") long id) {
        ModelAndView model = new ModelAndView("cadastroItens.html");
        Optional<Item> opcao = repositorioItem.findById(id);
        if (opcao.isPresent()) {
            Item item = opcao.get();
            model.addObject("item", item);
            return model;
        }

        return model;
    }

    @RequestMapping("/buscar_item")
    public ModelAndView buscar(String q) {
        ModelAndView model = new ModelAndView("listar_itens.html");
        Iterable<Item> itens = repositorioItem.findByNomeContaining(q);
        model.addObject("itens", itens);

        return model;
    }

}