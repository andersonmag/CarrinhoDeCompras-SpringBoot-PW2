package com.example.minhaloja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ControladorCarrinho {

    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/addCarrinho/{id}")
    public ModelAndView addCar(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("redirect:/listar_compras");
        Optional<Item> opcao = repositorioItem.findById(id);

        if (opcao.isPresent()) {
            Item item = opcao.get();
            List<Item> car = (ArrayList) request.getSession().getAttribute("car");

            if (car == null) {

                car = new ArrayList<>();
            }

            int i = 0;

            for (Item item2 : car) {

                if (car.get(i).getNome().equals(item.getNome())) {

                    return model;

                }

                i++;

            }

            car.add(item);
            request.getSession().setAttribute("car", car);

        }

        return model;
    }

    @RequestMapping("/listar_compras")
    public ModelAndView lista(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("compras");

        List<Item> car = (ArrayList) request.getSession().getAttribute("car");

        if (car == null) {
        
            car = new ArrayList<>();

        }

        if (car.isEmpty()) {
        
            model.addObject("mensagem", "Carrinho Vazio :(");
        }
        
        double valorTotal = 0.0;

        for (Item item : car) {

            valorTotal += item.getPreco();
        
        }

        model.addObject("car", car);
        model.addObject("valorTotal", valorTotal);

        return model;

    }

    @RequestMapping("/deleteCarrinho/{id}")
    public ModelAndView deletar(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("redirect:/listar_compras");

        Optional<Item> opcao = repositorioItem.findById(id);

        if (opcao.isPresent()) {
            Item item = opcao.get();
            List<Item> car = (ArrayList) request.getSession().getAttribute("car");

            if (car == null) {
                car = new ArrayList<>();
            }

            int i = 0;

            for (Item item2 : car) {

                if (car.get(i).getNome().equals(item.getNome())) {

                    car.remove(i);
                    request.getSession().setAttribute("car", car);

                    return model;
                }

                i++;
            }

        }

        return model;
    }

}