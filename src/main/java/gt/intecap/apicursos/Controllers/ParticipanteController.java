package gt.intecap.apicursos.Controllers;

import gt.intecap.apicursos.Models.CursoModel;
import gt.intecap.apicursos.Models.ParticipanteModel;
import gt.intecap.apicursos.Models.enums.Modalidad;
import gt.intecap.apicursos.Repository.CursoRepository;
import gt.intecap.apicursos.Repository.ParticipanteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteRepository participanteRepo;

    @GetMapping("/disponibles")
    public List<ParticipanteModel> listar() {
        return participanteRepo.findAll();
    }

    @GetMapping("/buscar")
    public List<ParticipanteModel> buscar(@RequestParam String termino) {
        return participanteRepo.buscarPorTexto(termino);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ParticipanteModel participante) {
        if (participanteRepo.buscarPorCorreo(participante.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }
        return ResponseEntity.ok(participanteRepo.save(participante));
    }
    // metodo actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ParticipanteModel detalles) {
        return participanteRepo.findById(id).map(p -> {
            p.setNombres(detalles.getNombres());
            p.setApellidos(detalles.getApellidos());
            p.setCorreo(detalles.getCorreo());
            p.setTelefono(detalles.getTelefono());
            return ResponseEntity.ok(participanteRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }
   // metodo para devolver alumnos activos
    @GetMapping
    public List<ParticipanteModel> listarTodos() {
        return participanteRepo.findAll().stream()
                .filter(ParticipanteModel::getActivo) // Filtra y deja solo los que tengan activo = true
                .toList();
    }
    //metodo hace un soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        return participanteRepo.findById(id).map(participante -> {
            //desactivamos el id del participante y se cambia por  estado de activacion
            participante.setActivo(false);
            participanteRepo.save(participante);
            return ResponseEntity.ok().body("Participante deshabilitado correctamente");
        }).orElse(ResponseEntity.notFound().build());//Retorna 404 si no existe

    }

}
