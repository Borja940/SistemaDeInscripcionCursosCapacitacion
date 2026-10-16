package gt.intecap.apicursos.Controllers;

import gt.intecap.apicursos.Models.CursoModel;
import gt.intecap.apicursos.Models.enums.Modalidad;
import gt.intecap.apicursos.Repository.CursoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepo;

    @GetMapping("/disponibles")
    public List<CursoModel> listar() {
        return cursoRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoModel> buscarPorId(@PathVariable Long id) {
        return cursoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nombre")
    public List<CursoModel> buscarPorNombre(@RequestParam String nombre) {
        return cursoRepo.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/modalidad")
    public List<CursoModel> buscarPorModalidad(@RequestParam Modalidad modalidad) {
        return cursoRepo.buscarPorModalidad(modalidad);
    }
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody CursoModel curso) {
        // Validación de código único
        if (cursoRepo.buscarPorCodigo(curso.getCodigo()).isPresent()) {
            return ResponseEntity.badRequest().body("El código del curso ya existe.");
        }
        return ResponseEntity.ok(cursoRepo.save(curso));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CursoModel> actualizar(@PathVariable long id, @Valid @RequestBody CursoModel cursoDetalles) {
        return cursoRepo.findById(id)
                .map(curso -> {
                    curso.setNombre(cursoDetalles.getNombre());
                    curso.setDescripcion(cursoDetalles.getDescripcion());
                    curso.setModalidad(cursoDetalles.getModalidad());
                    curso.setCupo(cursoDetalles.getCupo());
                    curso.setFechaInicio(cursoDetalles.getFechaInicio());
                    curso.setActivo(cursoDetalles.getActivo());
                    return ResponseEntity.ok(cursoRepo.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }
  //modificamos la lista general para que solo devuelva  los cursos que no han sido eliminados
    @GetMapping
    public List<CursoModel> listarTodos() {
        return cursoRepo.findAll().stream()
                .filter(CursoModel::getActivo) // Solo deja pasar los que tengan activo = true
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        return cursoRepo.findById(id).map(curso -> {
            //desactivamos el curso en lugar de borrarlo de la base de datos
            curso.setActivo(false);
            cursoRepo.save(curso);
            return ResponseEntity.ok().body("Curso deshabilitado correctamente ");
        }).orElse(ResponseEntity.notFound().build());//Retorna 404 si no existe

    }

}
