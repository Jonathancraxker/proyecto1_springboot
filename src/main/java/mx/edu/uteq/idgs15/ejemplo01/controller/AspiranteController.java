package mx.edu.uteq.idgs15.ejemplo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AspiranteController {

    @GetMapping("/adm")
    public String adm(Model model){
        model.addAttribute("message", "Bienvenido a la pagina de Admisiones");
        return "adm";
    }  
}
