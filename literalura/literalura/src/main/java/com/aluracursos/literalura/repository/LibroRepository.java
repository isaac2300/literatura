package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Busca un libro por título ignorando mayúsculas/minúsculas
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    // Busca libros por las siglas del idioma (ej: 'en', 'es')
    List<Libro> findByIdioma(String idioma);
}