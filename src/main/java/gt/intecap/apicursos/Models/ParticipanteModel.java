package gt.intecap.apicursos.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "participantes")
public class ParticipanteModel extends PersonaModel{

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es valido")
    @Column(unique = true)
    @Size(max =100)
    private String correo;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max =20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

}
