package gt.intecap.apicursos.Repository;

import gt.intecap.apicursos.Models.CursoModel;
import gt.intecap.apicursos.Models.enums.Modalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Long> {

    //Validamos que el codigo no se repita antes de guardar

    @Query("SELECT c FROM CursoModel c WHERE c.codigo = :codigo")
    Optional<CursoModel> buscarPorCodigo(String codigo);

    //filtro para buscar por texto en la pantall de cursos
    @Query("SELECT c FROM CursoModel c WHERE LOWER (c.nombre) LIKE LOWER(CONCAT('%',:nombre, '%?'))")
    List<CursoModel> buscarPorNombre(String nombre);

    // filtro rapido para  botones de modalidad presencial y virtual
    @Query ("SELECT c FROM CursoModel c WHERE c.modalidad = :modalidad")
    List<CursoModel> buscarPorModalidad(Modalidad modalidad);



}
