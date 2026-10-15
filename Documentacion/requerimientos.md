Sistema de inscripcion de cursos de capacitacion.

planteamiento del problema:

El centro de capacitación lleva  el control de cursos y participantes en hojas de cálculo. Hay inscripciones  duplicadas, cursos que exceden su cupo, por lo que actualmente no se sabe cuántos cupos quedan.
A continuación vamos a diseñar un servicio web  que centralice la información  y una aplicación  web que lo consuma.

Requerimientos funcionales prioritarios:
RF01-RF04: Gestión de cursos. Registrar un código único para los cursos, listar por nombre, filtrar por modalidad, editar y eliminar solo si hay inscripciones activas.
RF05-RF07: Gestión de los participantes: Registrar un correo único listar por nombre o correo, editar y eliminar.
RF08-RF11: Gestión de Inscripciones: las inscripciones de los participantes validando restricciones, impedir  inscripciones duplicadas, sin cupo o en cursos que no están activos, cancelar la inscripción y ver el detalle del curso con  inscritos y cupo disponibles.
RF12: Mostrar mensajes al usuario de éxito o error  después de cada operación.


Requerimientos NO Funcionales:
RNF01: Desarrollo backend utilizando Java"25.0.2" y Spring Boot 3.
RNF02 & RNF03: Arquitectura desacoplada mediante una API REST en JSON; el cliente web no accede directamente a la base de datos, solo consume servicios vía HTTP.
RNF04: Interfaz de usuario responsiva utilizando Bootstrap 5.
RNF05: Validación de datos doble, tanto en el cliente como en el servicio backend.
RNF06 & RNF07: Documentación con Javadoc en clases/métodos públicos y pruebas unitarias en la capa de servicio.
