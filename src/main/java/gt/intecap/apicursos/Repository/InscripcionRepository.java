package gt.intecap.apicursos.Repository;

import gt.intecap.apicursos.Models.InscripcionModel;
import gt.intecap.apicursos.Models.enums.EstadoInscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {


    // cuanta cuantos alumnos activos tiene un cuerso, para contar 40
    @Query ("SELECT COUNT(i) FROM InscripcionModel i WHERE i.curso.id = :cursoId AND i.estado = :estado")
    long contarActivas(Long cursoId, EstadoInscripcion estado);

    //verificar si un participante tiene una matricula activa en el mismo curso
    @Query("SELECT COUNT(i) > 0 FROM InscripcionModel i WHERE i.curso.id = :cursoId AND i.participante.id = :participanteId AND i.estado = 'ACTIVA'")
    boolean estaIncritoActivo(Long cursoId, Long participanteId);

    //Lista todas las inscripciones asociadas a un curso
    @Query("SELECT i FROM InscripcionModel i WHERE i.curso.id = :cursoId")
    List<InscripcionModel> buscarPorCurso(Long cursoId);


}
