package mx.edu.uteq.idgs15.ejemplo01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class DirectorioPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(length = 100)
    private String correo;

    @Column(length = 20)
    private String extension;

}