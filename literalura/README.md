📚 Challenge Literalura - Catálogo de Libros
¡Bienvenido al proyecto Literalura! Este es un buscador de libros interactivo desarrollado en Java 17 con Spring Boot, que consume la API de Gutendex para ofrecer información detallada sobre obras literarias y sus autores.

🚀 Funcionalidades
El sistema permite gestionar una biblioteca personal mediante un menú por consola con las siguientes opciones:

Buscar libro por título: Consulta la API externa y guarda el libro (y su autor) automáticamente en la base de datos PostgreSQL.

Listar libros registrados: Muestra todos los libros que ya han sido guardados localmente.

Listar autores registrados: Muestra una lista única de todos los autores en la base de datos.

Listar autores vivos en un determinado año: Filtra autores según su periodo de vida.

Listar libros por idioma: Filtra las obras por códigos de lenguaje (es, en, fr, pt).

🛠️ Tecnologías Utilizadas
Java 17

Spring Boot 3.2.4

Spring Data JPA (Persistencia de datos)

PostgreSQL (Base de datos relacional)

Jackson (Manipulación de JSON)

Maven (Gestión de dependencias)