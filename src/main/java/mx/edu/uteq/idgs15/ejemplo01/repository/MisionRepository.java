package mx.edu.uteq.idgs15.ejemplo01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.uteq.idgs15.ejemplo01.model.Mision;

public interface MisionRepository extends JpaRepository<Mision, 
Integer> {

}
