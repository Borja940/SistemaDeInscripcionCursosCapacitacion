package gt.intecap.apicursos.Repository;

import gt.intecap.apicursos.Models.ParticipanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<ParticipanteModel, Long> {

    //validar que el correo electronico sea unico
    @Query("SELECT p FROM ParticipanteModel p WHERE p.correo = :correo")
    Optional<ParticipanteModel> buscarPorCorreo(String correo);

    //Buscador por nommbres
    @Query(" SELECT p FROM ParticipanteModel p WHERE (LOWER(p.nombres) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :termino, '%'))) AND p.activo = true ")
    List<ParticipanteModel> buscarPorTexto(String termino);
}
