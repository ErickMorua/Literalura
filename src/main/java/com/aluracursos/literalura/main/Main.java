package com.aluracursos.literalura.main;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;

public class Main {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositoryLibro;
    private AutorRepository repositoryAutor;
    private List<Autor> autores;
    private List<Libro> libros;


    public Main(LibroRepository repositoryLibro, AutorRepository repositoryAutor) {
        this.repositoryLibro = repositoryLibro;
        this.repositoryAutor = repositoryAutor;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Top 10 Libros
                    0. salir""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    top10Libros();
                    break;
                case 0:
                    System.out.println("Aplicacion terminada");
                    break;
                default:
                    System.out.println("Error, Opcion Invalida");
            }
        }
    }



    private DatosBusqueda getDatosBusqueda() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", ""));
        System.out.println(json);
        DatosBusqueda datos = convierteDatos.obtenerDatos(json, DatosBusqueda.class);
        return datos;
    }

    private void buscarLibroPorTitulo() {
        DatosBusqueda datosBusqueda = getDatosBusqueda();
        if (datosBusqueda != null && !datosBusqueda.resultado().isEmpty()) {
            DatosLibros primerLibro = datosBusqueda.resultado().get(0);

            Libro libro = new Libro(primerLibro);
            System.out.println("-------------------------");
            System.out.println(libro);
            System.out.println("-------------------------");

            Optional<Libro> libroExiste = repositoryLibro.findByTitulo(libro.getTitulo());
            if (libroExiste.isPresent()) {
                System.out.println("\nEl libro ya esta registrado");
            } else {
                if (!primerLibro.autor().isEmpty()) {
                    DatosAutor autor = primerLibro.autor().get(0);
                    Autor autorPrimero = new Autor(autor);
                    Optional<Autor> autorOptional = repositoryAutor.findByNombre(autorPrimero.getNombre());

                    if (autorOptional.isPresent()) {
                        Autor autorExiste = autorOptional.get();
                        libro.setAutor(autorExiste);
                        repositoryLibro.save(libro);
                    } else {
                        Autor autorNuevo = repositoryAutor.save(autorPrimero);
                        libro.setAutor(autorNuevo);
                        repositoryLibro.save(libro);
                    }

                    Integer numeroDescargas = libro.getNumero_descargas() != null ? libro.getNumero_descargas() : 0;
                    System.out.printf(
                            "---------- Libro ----------%n" +
                                    "Título: %s%n" +
                                    "Autor: %s%n" +
                                    "Idioma: %s%n" +
                                    "Número de Descargas: %d%n" +
                                    "---------------------------%n",
                            libro.getTitulo(), autorPrimero.getNombre(), libro.getLenguaje(), libro.getNumero_descargas()
                    );

                } else {
                    System.out.println("Sin Autor");
                }
            }
        } else {
            System.out.println("Libro No encontrado");
        }
    }
    private void listarLibrosRegistrados() {
        libros = repositoryLibro.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = repositoryAutor.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);

    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("ingresa el año vivo de autor(es) que desea buscar");
        var anio = teclado.nextInt();
        autores = repositoryAutor.listaAutoresVivosPorAnio(anio);
        autores.stream()
                .sorted(Comparator.comparing(Autor::getFecha_nacimiento))
                .forEach(System.out::println);
    }

    private List<Libro> datosBusquedaLenguaje(String idioma) {
        var dato = Idioma.fromString(idioma);
        System.out.println("Lenguaje buscado" + dato);

        List<Libro> libroPorIdioma = repositoryLibro.findByLenguaje(dato);
        return libroPorIdioma;
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Selecciona el lenguaje/idioma que deseas buscar: ");

        var opcion = -1;
        while (opcion != 0) {
            var opciones ="""
            1. en - Ingles
            2. es - Español
            3. fr - Francés
            4. pt - Portugués

            0. Volver a Las opciones anteriores
            """;
            System.out.println(opciones);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato invalido, ingrese un número que este disponible en el menú");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Libro> librosEnIngles = datosBusquedaLenguaje("[en]");
                    librosEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Libro> librosEnEspanol = datosBusquedaLenguaje("[es]");
                    librosEnEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Libro> librosEnFrances = datosBusquedaLenguaje("[fr]");
                    librosEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Libro> librosEnPortugues = datosBusquedaLenguaje("[pt]");
                    librosEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ningún idioma seleccionado");
            }
}
}
    private void top10Libros() {
        List<Libro> topLibros = repositoryLibro.top10Libros();
        topLibros.forEach(System.out::println);
    }

}
