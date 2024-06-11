package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma lenguaje;
    private Integer numero_descargas;

    public Libro() {
    }


    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.lenguaje = Idioma.fromString(datosLibros.idiomas().toString().split(",")[0].trim());
        this.numero_descargas = datosLibros.numero_descargas();
    }

    @Override
    public String toString() {
        String nombreAutor = (autor != null) ? autor.getNombre() : "Autor desconocido";
        return String.format("________Libro________&nTitulo:" +
                "%s%nAutor: %s%nIdioma: %s%nNumero de Descargas:" +
                "%d%n_________________%n", titulo, nombreAutor, lenguaje, numero_descargas);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Idioma lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumero_descargas() {
        return numero_descargas;
    }

    public void setNumero_descargas(Integer numero_descargas) {
        this.numero_descargas = numero_descargas;
    }
}