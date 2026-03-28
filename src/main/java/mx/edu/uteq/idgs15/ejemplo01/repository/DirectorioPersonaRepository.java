package mx.edu.uteq.idgs15.ejemplo01.repository;

import mx.edu.uteq.idgs15.ejemplo01.model.DirectorioPersona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorioPersonaRepository extends JpaRepository<DirectorioPersona, 
Integer> {
    
}