package mx.edu.uteq.idgs15.ejemplo01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.edu.uteq.idgs15.ejemplo01.model.CapacidadTransversal;
import mx.edu.uteq.idgs15.ejemplo01.model.Division;
import mx.edu.uteq.idgs15.ejemplo01.model.OfertaEducativa;
import mx.edu.uteq.idgs15.ejemplo01.model.PerfilDeIngreso;
import mx.edu.uteq.idgs15.ejemplo01.repository.DivisionRepository;
import mx.edu.uteq.idgs15.ejemplo01.repository.PerfildeIngresoRepository;


@Controller
public class PerfilDeIngresoController {

@Autowired
private PerfildeIngresoRepository perfilRepo;
@Autowired
private mx.edu.uteq.idgs15.ejemplo01.repository.DivisionRepository divisionRepo;
@Autowired
private mx.edu.uteq.idgs15.ejemplo01.repository.OfertaEducativaRepository ofertaRepo;


@GetMapping("/consola/perfil")
@ResponseBody
@Transactional(readOnly = false)
public ResponseEntity<?> getMethodName() {

Division division = new Division();
division.setId(0);
division.setClave("DTAI");
division.setNombre("Division de Tecnologias de la Informacion");
division.setActivo(true);
divisionRepo.save(division);

OfertaEducativa oferta = new OfertaEducativa();
oferta.setNombreOferta("Ingenieria en Sistemas Computacionales");
oferta.setModalidad("Presencial");
oferta.setImagen("imagen.jpg");
oferta.setDivision(division);
ofertaRepo.save(oferta);

division.setProgramasEducativos(List.of(oferta));
divisionRepo.save(division);


PerfilDeIngreso perfil = new PerfilDeIngreso(
0,
"Perfil de Ingreso",
"Descripción del perfil de ingreso",
new java.util.ArrayList<>(
List.of(
new CapacidadTransversal("Capacidad 1"),
new CapacidadTransversal("Capacidad 2"))),
oferta);

perfilRepo.save(perfil);

return ResponseEntity.ok().body(perfil);
}

}