package gt.intecap.apicursos.Models;

import gt.intecap.apicursos.Models.Base.EntidadBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


//con esta anotacion transferimos los campos para heredar a participante
@MappedSuperclass
@Setter
@Getter
public abstract class PersonaModel extends EntidadBase {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max =100)
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max =100)
    private String apellidos;





    }




