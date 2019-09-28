package com.example.minhaloja.controle;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.example.minhaloja.modelo.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ControladorCarrinho{

    @RequestMapping("/listar_compras")
    public ModelAndView lista(HttpSession session, HttpServletRequest request){
        ModelAndView model = new ModelAndView("compras");

        List<Item> car = (ArrayList) request.getSession().getAttribute("car");

        model.addObject("car", car);
        return model;
    }

}