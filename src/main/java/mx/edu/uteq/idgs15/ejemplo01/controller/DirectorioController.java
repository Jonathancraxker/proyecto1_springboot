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

import mx.edu.uteq.idgs15.ejemplo01.model.DirectorioPersona;
import mx.edu.uteq.idgs15.ejemplo01.repository.DirectorioPersonaRepository;
import jakarta.validation.Valid;

@Controller
public class DirectorioController {

    @Autowired
    private DirectorioPersonaRepository repository;

    // Vista para usuario final
    @GetMapping("/directorio")
    public String directorio(Model model){
        
        List<DirectorioPersona> personas = repository.findAll();
            model.addAttribute("personas", personas);
        return "directorio/directorio";
    }

    // Listar todos los registros de personas en CRUD
    @GetMapping("/consola/directorio")
    public String directorioAdmin(Model model) {
        List<DirectorioPersona> personas = repository.findAll();
        model.addAttribute("personas", personas);
        return "directorio/directorioAdmin";
    }

    // Formulario para agregar
    @GetMapping("/consola/directorio/add")
    public String getFormularioDirectorio(Model model) {
    model.addAttribute("persona", new DirectorioPersona());
    model.addAttribute("titulo", "Agregar al Directorio");
    return "directorio/directorioForm";
    }

    // Formulario para editar
    @GetMapping("/consola/directorio/edit/{id}")
    public String getEditDirectorio(Model model, DirectorioPersona persona, 
        @PathVariable("id") int id) {
        model.addAttribute("titulo", "Editar Información Institucional");
        persona = repository.findById(id).orElseThrow(() -> 
        new IllegalArgumentException("Persona no encontrada: "));
        model.addAttribute("persona", persona);
        return "directorio/directorioForm";
    }

    // Eliminar registro
    @GetMapping("/consola/directorio/delete/{id}")
    public String getDeleteDirectorio(@PathVariable("id") int id) {
        DirectorioPersona persona = repository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Persona no encontrada: " + id));
        repository.delete(persona);
        return "redirect:/consola/directorio";
    }

    // Guardar Síncrono
    @PostMapping("/consola/directorio/save")
    public String postMethodName(@Valid @ModelAttribute("persona") DirectorioPersona persona,
    Errors errors) {
    if (errors.hasErrors()) {
    return "directorio/directorioForm";
    }
    repository.save(persona);
    return "redirect:/consola/directorio";
    }

    // API JSON (Para peticiones Asíncronas)

    @GetMapping("/api/directorio/{id}")
    @ResponseBody
    public ResponseEntity<DirectorioPersona> getPersona(@PathVariable Integer id) {
    DirectorioPersona persona = repository.findById(id)
    .orElseThrow(() -> new IllegalArgumentException("Invalid persona Id:" + id));
    return ResponseEntity.ok(persona);
    }

    @PostMapping(value = "/api/directorio/save",
    consumes = "application/json",
    produces = "application/json"
    )
    public ResponseEntity<?> savePersonaAsync(@Valid @RequestBody DirectorioPersona persona, Errors errores) {
    if (errores.hasErrors()) {
    return ResponseEntity.badRequest()
    .body(java.util.Map.of("success", false, "message", "Errores de validación"));
    }

    try {
    DirectorioPersona savedPersona = repository.save(persona);
    return ResponseEntity.ok(java.util.Map.of(
    "success", true,
    "message", "Persona guardada correctamente",
    "id", savedPersona.getId()
    ));
    } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(java.util.Map.of("success", false, "message", "Error al guardar la persona"));
    }
    }
}