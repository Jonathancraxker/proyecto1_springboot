package mx.edu.uteq.idgs15.ejemplo01.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.edu.uteq.idgs15.ejemplo01.model.Mision;
import mx.edu.uteq.idgs15.ejemplo01.repository.MisionRepository;
import jakarta.validation.Valid;

@Controller
public class MisionController {

    @Autowired
    private MisionRepository misionRepo;

    // --- Vista para usuarios finales ---
    @GetMapping("/mision")
    public String misiones(Model model) {
    List<Mision> todas = misionRepo.findAll();
    
    //filtro para obtener solo la misión activa
    Mision activa = todas.stream()
            .filter(Mision::getActivo)
            .findFirst()
            .orElse(null);

    model.addAttribute("misionActiva", activa);
    return "mision/mision";
    }

    // Listar todos los registros de Misión/Visión en CRUD
    @GetMapping("/consola/misiones")
    public String misionesAdmin(Model model) {
        List<Mision> misiones = misionRepo.findAll();
        model.addAttribute("misiones", misiones);
        return "mision/misionAdmin";
    }

    // Formulario para agregar
    @GetMapping("/consola/misiones/add")
    public String getFormularioMision(Model model) {
    model.addAttribute("misionObj", new Mision()); 
    model.addAttribute("titulo", "Agregar Información Institucional");
    return "mision/misionForm";
    }

    // Formulario para editar
    @GetMapping("/consola/misiones/edit/{id}")
    public String getEditMision(Model model, Mision mision, 
        @PathVariable("id") int id) {
        model.addAttribute("titulo", "Editar Información Institucional");
        mision = misionRepo.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("Misión no encontrada: "));
        model.addAttribute("misionObj", mision);
        return "mision/misionForm";
    }

    // Eliminar registro
    @GetMapping("/consola/misiones/delete/{id}")
    public String getDeleteMision(@PathVariable("id") int id) {
        Mision mision = misionRepo.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Registro no encontrado: " + id));
        misionRepo.delete(mision);
        return "redirect:/consola/misiones";
    }

    // Guardar (Síncrono)
    @PostMapping("/consola/misiones/save")
    public String postMethodName(@Valid @ModelAttribute("misionObj") Mision mision,
    Errors errors) {
    if (errors.hasErrors()) {
    return "mision/misionForm";
    }
    misionRepo.save(mision);
    return "redirect:/consola/misiones";
    }

    // API JSON (Para peticiones Asíncronas)

    @GetMapping("/api/mision/{id}")
    @ResponseBody
    public ResponseEntity<Mision> getMision(@PathVariable Integer id) {
    Mision mision = misionRepo.findById(id)
    .orElseThrow(() -> new IllegalArgumentException("Invalid mision Id:" + id));
    return ResponseEntity.ok(mision);
    }

    @PostMapping(value = "/api/mision/save",
    consumes = "application/json",
    produces = "application/json"
    )
    public ResponseEntity<?> saveMisionAsync(@Valid @RequestBody Mision mision, Errors errores) {
    if (errores.hasErrors()) {
    return ResponseEntity.badRequest()
    .body(java.util.Map.of("success", false, "message", "Errores de validación"));
    }

    try {
    Mision savedMision = misionRepo.save(mision);
    return ResponseEntity.ok(java.util.Map.of(
    "success", true,
    "message", "Misión guardada correctamente",
    "id", savedMision.getId()
    ));
    } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(java.util.Map.of("success", false, "message", "Error al guardar la misión"));
    }
    }
}