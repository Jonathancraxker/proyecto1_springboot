package mx.edu.uteq.idgs15.ejemplo01.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class OfertaEducativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nombreOferta;

    private String modalidad;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_division")
    private Division division;

}
