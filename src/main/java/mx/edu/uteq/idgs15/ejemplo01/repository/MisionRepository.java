package mx.edu.uteq.idgs15.ejemplo01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import mx.edu.uteq.idgs15.ejemplo01.model.Mision;

public interface MisionRepository extends JpaRepository<Mision, 
Integer> {
    Optional<Mision> findByActivoTrue();

    @Transactional
    @Modifying 
    @Query("UPDATE Mision m SET m.activo = false")
    void desactivarTodas();
}
