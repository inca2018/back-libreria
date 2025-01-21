package com.reto.biblioteca.config;

import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.repository.AutorRepository;
import com.reto.biblioteca.dao.repository.LibroRepository;
import com.reto.biblioteca.util.EstadoLibro;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadInitialData(AutorRepository autorRepository, LibroRepository libroRepository) {
        return args -> {
            // Verifica si ya existen autores y libros en la base de datos
            if (autorRepository.count() == 0 && libroRepository.count() == 0) {
                // Lista de autores
                List<Autor> autores = Arrays.asList(
                        Autor.builder()
                                .nombre("Gabriel García Márquez")
                                .nacionalidad("Colombiana")
                                .fechaNacimiento(LocalDate.of(1927, 3, 6))
                                .status(true)
                                .build(),
                        Autor.builder()
                                .nombre("Isabel Allende")
                                .nacionalidad("Chilena")
                                .fechaNacimiento(LocalDate.of(1942, 8, 2))
                                .status(true)
                                .build(),
                        Autor.builder()
                                .nombre("Mario Vargas Llosa")
                                .nacionalidad("Peruana")
                                .fechaNacimiento(LocalDate.of(1936, 3, 28))
                                .status(true)
                                .build(),
                        Autor.builder()
                                .nombre("Jorge Luis Borges")
                                .nacionalidad("Argentina")
                                .fechaNacimiento(LocalDate.of(1899, 8, 24))
                                .status(true)
                                .build(),
                        Autor.builder()
                                .nombre("Octavio Paz")
                                .nacionalidad("Mexicana")
                                .fechaNacimiento(LocalDate.of(1914, 3, 31))
                                .status(true)
                                .build()
                );

                // Guarda los autores en la base de datos
                autorRepository.saveAll(autores);

                // Lista de libros con información específica
                List<Libro> libros = new ArrayList<>();

                libros.addAll(Arrays.asList(
                        // Libros de Gabriel García Márquez
                        Libro.builder().titulo("Cien años de soledad").autor(autores.get(0)).isbn("978-1").fechaPublicacion(LocalDate.of(1967, 6, 5)).estado(EstadoLibro.DISPONIBLE).status(true).build(),
                        Libro.builder().titulo("El amor en los tiempos del cólera").autor(autores.get(0)).isbn("978-2").fechaPublicacion(LocalDate.of(1985, 9, 15)).estado(EstadoLibro.DISPONIBLE).status(true).build(),

                        // Libros de Isabel Allende
                        Libro.builder().titulo("La casa de los espíritus").autor(autores.get(1)).isbn("978-5").fechaPublicacion(LocalDate.of(1982, 11, 1)).estado(EstadoLibro.DISPONIBLE).status(true).build(),

                        // Libros de Mario Vargas Llosa
                        Libro.builder().titulo("La ciudad y los perros").autor(autores.get(2)).isbn("978-9").fechaPublicacion(LocalDate.of(1963, 1, 1)).estado(EstadoLibro.DISPONIBLE).status(true).build(),

                        // Libros de Jorge Luis Borges
                        Libro.builder().titulo("Ficciones").autor(autores.get(3)).isbn("978-13").fechaPublicacion(LocalDate.of(1944, 9, 1)).estado(EstadoLibro.DISPONIBLE).status(true).build(),

                        // Libros de Octavio Paz
                        Libro.builder().titulo("El laberinto de la soledad").autor(autores.get(4)).isbn("978-17").fechaPublicacion(LocalDate.of(1950, 1, 1)).estado(EstadoLibro.DISPONIBLE).status(true).build(),
                        Libro.builder().titulo("Piedra de sol").autor(autores.get(4)).isbn("978-18").fechaPublicacion(LocalDate.of(1957, 3, 1)).estado(EstadoLibro.DISPONIBLE).status(true).build()

                ));

                // Guarda todos los libros en la base de datos
                libroRepository.saveAll(libros);

                System.out.println("Datos iniciales cargados correctamente.");
            } else {
                System.out.println("Los datos ya existen en la base de datos. No se realizaron inserciones.");
            }
        };
    }
}
