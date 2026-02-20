package mx.edu.uteq.idgs15.ejemplo01.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PerfilDeIngreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String descripcion;
    private List<String> habilidadesTransversales;
    private List<String> competenciasEspecificas;

    

    public PerfilDeIngreso() {
    }

    public PerfilDeIngreso(int id, String descripcion, List<String> habilidadesTransversales,
            List<String> competenciasEspecificas) {
        this.id = id;
        this.descripcion = descripcion;
        this.habilidadesTransversales = habilidadesTransversales;
        this.competenciasEspecificas = competenciasEspecificas;
    }
}
