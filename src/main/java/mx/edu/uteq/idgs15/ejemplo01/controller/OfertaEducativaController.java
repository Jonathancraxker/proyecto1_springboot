package mx.edu.uteq.idgs15.ejemplo01.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
            model.addAttribute("divisiones", divisionRepo.findAll());
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

    @GetMapping("/api/oferta/{id}")
    @ResponseBody
    public ResponseEntity<OfertaEducativa> getOferta(@PathVariable Integer id) {
    OfertaEducativa oferta = repo.findById(id)
    .orElseThrow(() -> new IllegalArgumentException("Invalid oferta Id:" + id));
    return ResponseEntity.ok(oferta);
    }

    @PostMapping(value = "/api/oferta/save",
    consumes = "application/json",
    produces = "application/json"
    )
    public ResponseEntity<?> saveOfertaAsync(@Valid @RequestBody OfertaEducativa oferta, Errors errores) {
    if (errores.hasErrors()) {
    return ResponseEntity.badRequest()
    .body(java.util.Map.of("success", false, "message", "Errores de validación"));
    }

    try {
    OfertaEducativa savedOferta = repo.save(oferta);
    return ResponseEntity.ok(java.util.Map.of(
    "success", true,
    "message", "Oferta Educativa guardada correctamente",
    "id", savedOferta.getId()
    ));
    } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(java.util.Map.of("success", false, "message", "Error al guardar la oferta educativa"));
    }
    }

    }
