package gt.intecap.apicursos.Models;

import gt.intecap.apicursos.Models.Base.EntidadBase;
import gt.intecap.apicursos.Models.enums.EstadoInscripcion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inscripciones")
public class InscripcionModel extends EntidadBase {

    @NotNull(message = "El curso es obligatorio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)//carga los datos del curso de la BD si se realmente se necesita
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoModel curso;


    @NotNull(message = "El participante es obligatorio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY) //Carga los datos del participante de la DB si se necesita
    @JoinColumn(name = "participante_id", nullable = false)
    private ParticipanteModel participante;

    @NotNull(message = "El estado de la inscripcion es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInscripcion estado;




}
