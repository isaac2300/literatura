package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = Integer.valueOf(datosAutor.fechaDeNacimiento());
        this.fechaDeFallecimiento = Integer.valueOf(datosAutor.fechaDeFallecimiento());
    }

    @Override
    public String toString() {
        String titulosLibros = (libros != null) ?
                libros.stream().map(Libro::getTitulo).collect(Collectors.joining(", ")) : "N/A";

        return "---------- AUTOR ----------" +
                "\nNombre: " + nombre +
                "\nFecha de nacimiento: " + fechaDeNacimiento +
                "\nFecha de fallecimiento: " + (fechaDeFallecimiento != null ? fechaDeFallecimiento : "N/A") +
                "\nLibros: [ " + titulosLibros + " ]" +
                "\n---------------------------";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Integer getFechaDeNacimiento() { return fechaDeNacimiento; }
    public Integer getFechaDeFallecimiento() { return fechaDeFallecimiento; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}