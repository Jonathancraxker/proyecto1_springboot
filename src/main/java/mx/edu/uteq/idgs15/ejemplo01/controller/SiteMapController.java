package mx.edu.uteq.idgs15.ejemplo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteMapController {
    @GetMapping("/sitemap")
    public String otroMetodo(Model model) {
        model.addAttribute("title", "Bienvenido al Mapa de Sitio");
        return "sitemap";
    }
    
    @GetMapping("/demo")
    public String demoMetodo(Model model) {
        model.addAttribute("title", "Bienvenido al Demo");
        return "demo";
    }
}
