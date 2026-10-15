Estructura General del Sistema:
El sistema se divide de manera independiente en dos grandes bloques:
Fronted: Cliente web las vistas en Framework Bootstrap 5
Backend: Servicio API REST en Spring Boot 3 + Java 25 conexión interna a Base de Datos en memoria H2 DataBase.

Arquitectura de Capas en el Backend (Spring Boot)
Dentro del Backend, el código se organiza en 4 capas verticales bien definidas. El flujo de datos siempre viaja de arriba hacia abajo:
Capa controladores REST: recibe solicitudes HTTP y devuelve JSON
Capa servicios: Contiene la lógica del negocio y validaciones
Capa acceso a datos: maneja las consultas SQL
Base de datos: almacenamiento temporal de la información.
Estructura:
La estructura de directorios en el backend se organizará de la siguiente manera:
Controller
Services
Repository
Models
