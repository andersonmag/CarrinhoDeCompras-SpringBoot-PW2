package com.example.minhaloja.controle;

import javax.validation.Valid;

import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorItem{
    
    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/formulario_item")
    public ModelAndView formularioItem(Item item){
        ModelAndView retorno = new ModelAndView("cadastroItens.html");
        return retorno;
    }

    @RequestMapping("/novo_item")
    public ModelAndView cadastroCliente(@Valid Item item, BindingResult bidingResult, RedirectAttributes redirect){
        ModelAndView retorno;
        if(bidingResult.hasErrors()){            
            redirect.addFlashAttribute("item", item); 
            retorno = new ModelAndView("cadastroItens.html");
            return retorno;
        }
        retorno = new ModelAndView("redirect:/");
        repositorioItem.save(item);  
        redirect.addFlashAttribute("mensagem", "Item cadastrado com sucesso!");     
        return retorno;
    }
}