package com.example.minhaloja.controle;

import java.io.IOException;
import java.util.Optional;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Imagem;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioImagem;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorItem {

    @Autowired
    RepositorioItem repositorioItem;
    @Autowired
    RepositorioImagem repositorioImagem;
    @Autowired
    RepositorioTime repositorioTime;

    @GetMapping("/")
    public ModelAndView index() {

        ModelAndView model = new ModelAndView("index");
        model.addObject("produtos", repositorioItem.findAll())
                .addObject("cp", repositorioItem.findByTime(repositorioTime.findById(1L).get()))
                .addObject("ci", repositorioItem.findByTime(repositorioTime.findById(4L).get()));
        return model;
    }

    @GetMapping("/formulario_item")
    public ModelAndView formularioItem(Item item) {
        return new ModelAndView("item/cadastroItens");
    }

    @GetMapping("/localcloud/image/{id}")
    public byte[] obterImagem(@PathVariable Long id) {
        return repositorioItem.findById(id).get().getImagem().getDados();
    }

    @PostMapping("/formulario_item")
    public ModelAndView cadastroItem(@Valid Item item, BindingResult bidingResult, RedirectAttributes redirect,
            @RequestParam(value = "imagemItem") MultipartFile imagemItem) throws IOException {

        if (bidingResult.hasErrors()) {
            redirect.addFlashAttribute("item", item);
            return new ModelAndView("cadastroItens");
        }

        if (imagemItem != null)
            item.setImagem(repositorioImagem.save(
                    new Imagem(imagemItem.getBytes(), imagemItem.getOriginalFilename(), imagemItem.getContentType())));

        repositorioItem.save(item);
        redirect.addFlashAttribute("message", item.getNome() + " cadastrado com sucesso!");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/listar_itens")
    public ModelAndView listar() {
        ModelAndView model = new ModelAndView("listar_itens");
        model.addObject("itens", repositorioItem.findAll());
        return model;
    }

    @RequestMapping("/excluir_item/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Optional<Item> opcao = repositorioItem.findById(id);
        ModelAndView model = new ModelAndView("redirect:/listar_itens");

        if (opcao.isPresent())
            repositorioItem.deleteById(opcao.get().getId());
        redirect.addFlashAttribute("mensagem", "Id não cadastrado!");
        return model;
    }

    @RequestMapping("/atualizar_item/{id}")
    public ModelAndView atualizar(@PathVariable long id, RedirectAttributes redirect) {
        ModelAndView model = new ModelAndView("item/item.html");
        Optional<Item> opcao = repositorioItem.findById(id);
        if (opcao.isPresent()) {
            model.addObject("item", opcao.get());
            return model;
        }
        model.setViewName("redirect:/listar_itens");
        redirect.addFlashAttribute("mensagem", "Id não cadastrado!");
        return model;
    }

    @GetMapping("/buscar_item")
    public ModelAndView buscar(String q) {
        ModelAndView model = new ModelAndView("listar_itens");
        model.addObject("itens", repositorioItem.findByNomeContaining(q));
        return model;
    }
}