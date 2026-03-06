package mx.edu.uteq.idgs15.ejemplo01.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Crea todos los getters y setters
@AllArgsConstructor // Crea constructores con todos los atributos
@NoArgsConstructor
@Entity
public class PerfilDeIngreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "perfil_id")
    private List<CapacidadTransversal> capacidadesTransversales;

    @OneToOne()
    @JoinColumn(name = "oferta_id")
    private OfertaEducativa ofertaEducativa;
}