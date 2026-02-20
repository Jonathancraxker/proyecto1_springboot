package mx.edu.uteq.idgs15.ejemplo01.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.edu.uteq.idgs15.ejemplo01.model.OfertaEducativa;
import mx.edu.uteq.idgs15.ejemplo01.repository.DivisionRepository;
import mx.edu.uteq.idgs15.ejemplo01.repository.OfertaEducativaRepository;
import jakarta.validation.Valid;


@Controller
public class OfertaEducativaController {

    @Autowired
    private OfertaEducativaRepository repo;
    @Autowired
    private DivisionRepository divisionRepo; 

    

    @GetMapping("/consola/oferta-educativa/add")
    public String getFormulario(Model model, OfertaEducativa ofertaEducativa) {
        model.addAttribute("oferta", ofertaEducativa);
        model.addAttribute("titulo", "Agregar Nueva Oferta Educativa");
        model.addAttribute("divisiones", divisionRepo.findAll());
        return "ofertaEducativaForm";
    }    
    
    @GetMapping("/consola/oferta-educativa/edit/{id}")
    public String getEditOferta(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("titulo", "Editar Oferta Educativa");
        OfertaEducativa oferta = repo.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("Oferta Educativa no encontrada: "));
        model.addAttribute("oferta", oferta);
        
        model.addAttribute("divisiones", divisionRepo.findAll());
        return "ofertaEducativaForm";
    }

    @GetMapping("/consola/oferta-educativa/delete/{id}")
    public String getDeleteOferta(Model model, OfertaEducativa ofertaEducativa, 
        @PathVariable("id") int id) {
        model.addAttribute("titulo", "Eliminar Oferta Educativa");
        ofertaEducativa = repo.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("Oferta Educativa no encontrada: "));
        repo.delete(ofertaEducativa);
        return "redirect:/consola/oferta-educativa";
    }

    @GetMapping("/consola/oferta-educativa")
    public String ofertaEducativaAdmin(Model model){
        List<OfertaEducativa> ofertas = repo.findAll();
            model.addAttribute("ofertas", ofertas);
        return "ofertaEducativaAdmin";
    }

    // @GetMapping("/consola/oferta-list")
    // public String ofertaConsolaAdmin (Model model) {

    //     List<OfertaEducativa> ofertas = repo.findAll();
    //     model.addAttribute("ofertas", ofertas);
    //     return "OfertaEducativaAdmin";
    // }

    @GetMapping("/oferta-educativa")
    public String ofertaEducativa(Model model){
        
        List<OfertaEducativa> ofertas = repo.findAll();
            model.addAttribute("ofertas", ofertas);
        // if (ofertas.isEmpty()) {

        // OfertaEducativa oferta4 = new OfertaEducativa();
        // oferta4.setNombreOferta("Ingeniería Ambiental y Sustentabilidad");
        // oferta4.setModalidad("");
        // oferta4.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LIAS.png");
        // ofertas.add(oferta4);

        // OfertaEducativa oferta5 = new OfertaEducativa();
        // oferta5.setNombreOferta("Agricultura Sustentable y Protegida");
        // oferta5.setModalidad("");
        // oferta5.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LTII.png");
        // ofertas.add(oferta5);

        // OfertaEducativa oferta6 = new OfertaEducativa();
        // oferta6.setNombreOferta("Licenciatura en Administración");
        // oferta6.setModalidad("");
        // oferta6.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LA.png");
        // ofertas.add(oferta6);

        // OfertaEducativa oferta7 = new OfertaEducativa();
        // oferta7.setNombreOferta("Licenciatura en Negocios y en Mercadotecnia");
        // oferta7.setModalidad("");
        // oferta7.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LNM.png");
        // ofertas.add(oferta7);

        // OfertaEducativa oferta8 = new OfertaEducativa();
        // oferta8.setNombreOferta("Ingenieria en Logistica");
        // oferta8.setModalidad("");
        // oferta8.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LTII.png");
        // ofertas.add(oferta8);

        // OfertaEducativa oferta9 = new OfertaEducativa();
        // oferta9.setNombreOferta("Licenciatura en Contaduria");
        // oferta9.setModalidad("Modalidad vespertina y mixta");
        // oferta9.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/contaduria.png");
        // ofertas.add(oferta9);

        // OfertaEducativa oferta10 = new OfertaEducativa();
        // oferta10.setNombreOferta("Ingenieria en mantenimiento Industrial");
        // oferta10.setModalidad("");
        // oferta10.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LIMI.png");
        // ofertas.add(oferta10);

        // OfertaEducativa oferta11 = new OfertaEducativa();
        // oferta11.setNombreOferta("Ingenieria en Nanotecnologia");
        // oferta11.setModalidad("");
        // oferta11.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LIN.png");
        // ofertas.add(oferta11);

        // OfertaEducativa oferta12 = new OfertaEducativa();
        // oferta12.setNombreOferta("Ingenieria industrial");
        // oferta12.setModalidad("");
        // oferta12.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LII.png");
        // ofertas.add(oferta12);

        // OfertaEducativa oferta13 = new OfertaEducativa();
        // oferta13.setNombreOferta("Ingenieria Mecanica");
        // oferta13.setModalidad("");
        // oferta13.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LIM.png");
        // ofertas.add(oferta13);

        // OfertaEducativa oferta14 = new OfertaEducativa();
        // oferta14.setNombreOferta("Ingenieria Mecanica Automotriz");
        // oferta14.setModalidad("");
        // oferta14.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/mecanica.png");
        // ofertas.add(oferta14);

        // OfertaEducativa oferta15 = new OfertaEducativa();
        // oferta15.setNombreOferta("Ingenieria en Microelectrónica y Semiconductores");
        // oferta15.setModalidad("");
        // oferta15.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LIMI.png");
        // ofertas.add(oferta15);

        // OfertaEducativa oferta16 = new OfertaEducativa();
        // oferta16.setNombreOferta("Licenciatura en Educación");
        // oferta16.setModalidad("en Enseñanza del idioma inglés");
        // oferta16.setImagen("https://www.uteq.edu.mx/Images/OfertaEducativa/LE.png");
        // ofertas.add(oferta16);

        return "ofertaEducativa";
    }

    // @PostMapping("/consola/oferta-educativa/save")
    // public String postGuardarOferta(@Valid OfertaEducativa ofertaEducativa, Errors errors, Model model) {
    //     if (errors.hasErrors()) {
    //         model.addAttribute("titulo", ofertaEducativa.getId() == null ? "Agregar Nueva Oferta Educativa" : "Editar Oferta Educativa");
    //         model.addAttribute("divisiones", divisionRepo.findAll());
    //         return "ofertaEducativaForm";
    //     }
    //     repo.save(ofertaEducativa);
    //     return "redirect:/consola/oferta-educativa";
    // }

    @PostMapping("/consola/oferta-educativa/save")
    public String postMethodName(@Valid OfertaEducativa ofertaEducativa,
    Errors errors) {
    if (errors.hasErrors()) {
    return "ofertaEducativaForm";
    }
    repo.save(ofertaEducativa);
    return "redirect:/consola/oferta-educativa";
    }



    }
