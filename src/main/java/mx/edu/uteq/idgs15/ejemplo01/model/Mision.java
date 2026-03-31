package mx.edu.uteq.idgs15.ejemplo01.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Mision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(length = 500)
    private String mision;

    @NotEmpty
    @Column(length = 500)
    private String vision;

    @NotEmpty
    @Column(length = 500)
    private String politica;

    @NotEmpty
    @Column(length = 500)
    private String objetivos;

    @NotEmpty
    @Column(length = 500)
    private String valores;

    private String fecha;

    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}