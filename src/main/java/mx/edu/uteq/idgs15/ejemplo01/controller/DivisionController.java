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

import mx.edu.uteq.idgs15.ejemplo01.model.Division;
import mx.edu.uteq.idgs15.ejemplo01.model.OfertaEducativa;
import mx.edu.uteq.idgs15.ejemplo01.repository.DivisionRepository;
import mx.edu.uteq.idgs15.ejemplo01.repository.OfertaEducativaRepository;
import jakarta.validation.Valid;


@Controller
public class DivisionController {

    @Autowired
    private OfertaEducativaRepository repo;
    @Autowired
    private DivisionRepository divisionRepo; 

    @GetMapping("/consola/divisiones")
    public String ofertaEducativaDivisionesAdmin(Model model){
        List<OfertaEducativa> ofertas = repo.findAll();
            model.addAttribute("ofertas", ofertas);
            model.addAttribute("divisiones", divisionRepo.findAll());
            return "division/divisionAdmin";
    }

    @GetMapping("/consola/divisiones/add")
    public String getFormulario(Model model) {
        model.addAttribute("division", new Division());
        model.addAttribute("titulo", "Agregar Nueva División");
        return "division/divisionForm";
    }    
    
    @GetMapping("/consola/divisiones/edit/{id}")
    public String getEditDivision(Model model, Division division, 
        @PathVariable("id") int id) {
        model.addAttribute("titulo", "Editar División");
        division = divisionRepo.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("División no encontrada: "));
        model.addAttribute("division", division);
        model.addAttribute("divisiones", divisionRepo.findAll());
        return "division/divisionForm";
    }

    @GetMapping("/consola/divisiones/delete/{id}")
    public String getDeleteOferta(Model model, Division division,
        @PathVariable("id") int id) {
        model.addAttribute("titulo", "Eliminar División");
        division = divisionRepo.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("División no encontrada: "));
        divisionRepo.delete(division);
        return "redirect:/consola/divisiones";
    }

    // @PostMapping("/consola/divisiones/save")
    // public String postMethodName(@Valid Division division,
    // Errors errors, Model model) {
    // if (errors.hasErrors()) {
    //     model.addAttribute("titulo", division.getId() == 0 ? "Agregar División" : "Editar División");
    // return "division/divisionForm";
    // }
    // divisionRepo.save(division);
    // return "redirect:/consola/divisiones";
    // }

    
    //Método para el controlador de Division
@PostMapping("/consola/divisiones/save")
public String postMethodName(@Valid Division division,
Errors errors) {
if (errors.hasErrors()) {
return "division/divisionForm";
}
divisionRepo.save(division);
return "redirect:/consola/divisiones";
}

@GetMapping("/api/division/{id}")
@ResponseBody
public ResponseEntity<Division> getDivision(@PathVariable Integer id) {
Division division = divisionRepo.findById(id)
.orElseThrow(() -> new IllegalArgumentException("Invalid division Id:" + id));
return ResponseEntity.ok(division);
}

@PostMapping(value = "/api/division/save",
consumes = "application/json",
produces = "application/json"
)
public ResponseEntity<?> saveDivisionAsync(@Valid @RequestBody Division division, Errors errores) {
if (errores.hasErrors()) {
return ResponseEntity.badRequest()
.body(java.util.Map.of("success", false, "message", "Errores de validación"));
}

try {
Division savedDivision = divisionRepo.save(division);
return ResponseEntity.ok(java.util.Map.of(
"success", true,
"message", "División guardada correctamente",
"id", savedDivision.getId()
));
} catch (Exception e) {
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
.body(java.util.Map.of("success", false, "message", "Error al guardar la división"));
}
}

    }
