package gt.intecap.apicursos.Models;

import gt.intecap.apicursos.Models.Base.EntidadBase;
import gt.intecap.apicursos.Models.enums.Modalidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@Table(name= "cursos")
public class CursoModel extends EntidadBase {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50)
    @Column(unique = true, nullable = false)
    private String codigo;

    @NotBlank(message = "El nombre es obligarorio")
    @Size(max = 150)
    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "La modalidad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

    @NotNull(message = "El cupo es obligario")
    @Min(value= 1, message = "El cupo mínimo debe ser 1 participante")
    @Max(value= 40, message = "El cupo máximo debe ser 40 participantes")
    @Column(nullable = false)
    private Integer cupo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio",nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private Boolean activo = true;

}
