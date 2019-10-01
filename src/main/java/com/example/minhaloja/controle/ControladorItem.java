package com.example.minhaloja.controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    public ModelAndView cadastroItem(@Valid Item item, BindingResult bidingResult,
     RedirectAttributes redirect,@RequestParam(value = "imagem", required = false) MultipartFile imagem){
        ModelAndView retorno;
        if (bidingResult.hasErrors()) {
            redirect.addFlashAttribute("item", item);
            retorno = new ModelAndView("cadastroItens.html");
            return retorno;
        }

        retorno = new ModelAndView("redirect:/");
        repositorioItem.save(item);

        if(imagem.isEmpty() == false){
            String nomeImagem = CriarDiretorio(item, imagem);
            item.setNomeImagem(nomeImagem);

            repositorioItem.save(item);
        }
        
        redirect.addFlashAttribute("message", item.getNome() + " cadastrado com sucesso!");
        return retorno;
    }

    private String CriarDiretorio(Item item, MultipartFile imagem){

        try {
            byte[] conteudo = imagem.getBytes();
            Path caminhoArquivo = Paths.get("src/main/resources/static/img" + File.separator + "itens"
            + File.separator  + item.getId());

            if(Files.exists(caminhoArquivo) == false){
                Files.createDirectories(caminhoArquivo);
            }

            Files.write(caminhoArquivo.resolve(imagem.getOriginalFilename()), conteudo);
            return imagem.getOriginalFilename().toString();
            
        } catch (Exception e) {
            e.getMessage();
        }

        return "";
    }

    @RequestMapping("/listar_itens")
    public ModelAndView listar(HttpServletRequest request, HttpSession session) {
        ModelAndView model = new ModelAndView("listar_itens.html");
        Iterable<Item> itens = repositorioItem.findAll();

        model.addObject("itens", itens);

        List<Item> car = (ArrayList) request.getSession().getAttribute("car");

        if(car == null){
            car = new ArrayList<>();
        }

        request.getSession().setAttribute("car", car);

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