package gt.intecap.apicursos.Models.Base;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class EntidadBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //metodo para identificar y comparar objetos
    @Override
    public boolean equals(Object o){
        //validamos que la identidad apunte al mismo espacio en memoria, que sean identicos
        if (this == o) return true;
        //validamos si el otro objeto en nulo y si es de otra clase distinta
        if (o == null || getClass() != o.getClass())return false;

        //convetirmos el objeto o a entidadBase para obtener el id
        EntidadBase otro = (EntidadBase)  o;

        //comparamos que el id actual, ya este asignado, y si tienen el mismo valor numerico
        return id != null && id.equals(otro.id);
    }

    //metodo para transformar el objeto en un numero entero unico
    @Override
    //
    public int hashCode(){
        return getClass().hashCode();
    }

}
