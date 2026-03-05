package com.aluracursos.literalura;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner lectura = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository repositorio;
    private final AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n***************************************************
                    LITERALURA - BUSCADOR DE LIBROS
                    1 - Buscar libro por título (Web)
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ***************************************************
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(lectura.nextLine());

                switch (opcion) {
                    case 1 -> buscarLibroWeb();
                    case 2 -> mostrarLibrosRegistrados();
                    case 3 -> mostrarAutoresRegistrados();
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLibrosPorIdioma();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingresa un número válido.");
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nombreLibro = lectura.nextLine();
        String URL_BASE = "https://gutendex.com/books/";
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        if (datosBusqueda != null && !datosBusqueda.resultados().isEmpty()) {
            DatosLibro datosLibro = datosBusqueda.resultados().get(0);

            // Verificar si ya existe en la DB
            Optional<Libro> libroExistente = repositorio.findByTituloContainsIgnoreCase(datosLibro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("❌ El libro ya está registrado en la base de datos.");
            } else {
                DatosAutor datosAutor = datosLibro.autor().get(0);
                Autor autor = autorRepositorio.findByNombreIgnoreCase(datosAutor.nombre())
                        .orElseGet(() -> autorRepositorio.save(new Autor(datosAutor)));

                Libro libro = new Libro(datosLibro, autor);
                repositorio.save(libro);
                System.out.println("\n✅ Libro guardado con éxito:");
                System.out.println(libro);
            }
        } else {
            System.out.println("❌ Libro no encontrado.");
        }
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();
        if (libros.isEmpty()) System.out.println("No hay libros registrados.");
        libros.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();
        if (autores.isEmpty()) System.out.println("No hay autores registrados.");
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año para consultar autores vivos:");
        try {
            var anio = Integer.parseInt(lectura.nextLine());
            List<Autor> autores = autorRepositorio.buscarAutoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Año no válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el código del idioma:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var idioma = lectura.nextLine();
        List<Libro> libros = repositorio.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }
}