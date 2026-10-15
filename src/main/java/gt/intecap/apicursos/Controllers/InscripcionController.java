package gt.intecap.apicursos.Controllers;

import gt.intecap.apicursos.Models.CursoModel;
import gt.intecap.apicursos.Models.InscripcionModel;
import gt.intecap.apicursos.Models.ParticipanteModel;
import gt.intecap.apicursos.Models.enums.EstadoInscripcion;
import gt.intecap.apicursos.Repository.CursoRepository;
import gt.intecap.apicursos.Repository.InscripcionRepository;
import gt.intecap.apicursos.Repository.ParticipanteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionRepository inscripcionRepo;
    private final CursoRepository cursoRepo;
    private final ParticipanteRepository participanteRepo;

    // Listar solo inscripciones de cursos y participantes que NO hayan sufrido Soft Delete
    @GetMapping
    public List<InscripcionModel> listarTodas() {
        return inscripcionRepo.findAll().stream()
                .filter(i -> i.getCurso().getActivo() && i.getParticipante().getActivo())
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> inscribir(@Valid @RequestBody InscripcionModel inscripcion) {
        if (inscripcion.getCurso() == null || inscripcion.getCurso().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del curso es obligatorio.");
        }
        if (inscripcion.getParticipante() == null || inscripcion.getParticipante().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del participante es obligatorio.");
        }

        Long cursoId = inscripcion.getCurso().getId();
        Long participanteId = inscripcion.getParticipante().getId();

        // Validar que el curso exista y NO tenga Soft Delete (activo = true)
        CursoModel curso = cursoRepo.findById(cursoId).orElse(null);
        if (curso == null || !curso.getActivo()) {
            return ResponseEntity.badRequest().body("El curso seleccionado no existe, ha sido eliminado o está inactivo.");
        }
        // Validar que el participante exista y NO tenga Soft Delete (activo = true)
        ParticipanteModel participante = participanteRepo.findById(participanteId).orElse(null);
        if (participante == null || !participante.getActivo()) {
            return ResponseEntity.badRequest().body("El participante seleccionado no existe o ha sido deshabilitado.");
        }

        // Validar que el alumno no esté ya inscrito de forma ACTIVA
        if (inscripcionRepo.estaIncritoActivo(cursoId, participanteId)){
            return ResponseEntity.badRequest().body("Este participante ya se encuentra inscrito activamente en este curso.");
        }

        // Validar el tope de cupos dinámicos del curso (usando la consulta corta de conteo)
        long inscritosActivos = inscripcionRepo.contarActivas(cursoId, EstadoInscripcion.Activo);
        if (inscritosActivos >= curso.getCupo()) {
            return ResponseEntity.badRequest().body("No hay cupos disponibles. El límite de este curso es de " + curso.getCupo() + " participantes.");
        }

        // Asignar los objetos completos de la BD, la fecha de inscripción con estado activo
        inscripcion.setCurso(curso);
        inscripcion.setParticipante(participante);
        inscripcion.setEstado(EstadoInscripcion.Activo);

        return ResponseEntity.ok(inscripcionRepo.save(inscripcion));
    }

    // Cancelar matrícula para liberar el cupo
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarInscripcion(@PathVariable Long id) {
        return inscripcionRepo.findById(id).map(inscripcion -> {
            inscripcion.setEstado(EstadoInscripcion.Cancelado);
            inscripcionRepo.save(inscripcion);
            return ResponseEntity.ok().body("Inscripción cancelada con éxito. El cupo ha sido liberado.");
        }).orElse(ResponseEntity.notFound().build());
    }

    // Listar inscritos de un curso,filtrando que el participante siga activo
    @GetMapping("/curso/{cursoId}")
    public List<InscripcionModel> listarPorCurso(@PathVariable Long cursoId) {
        return inscripcionRepo.buscarPorCurso(cursoId).stream()
                .filter(i -> i.getParticipante().getActivo())
                .toList();
    }
}


