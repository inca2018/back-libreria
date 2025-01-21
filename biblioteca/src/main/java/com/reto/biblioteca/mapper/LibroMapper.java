package com.reto.biblioteca.mapper;

import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.expose.dto.LibroDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LibroMapper {

    public LibroDTO entityToDTO(Libro libro) {
        if (libro == null) {
            return null;
        }

        return LibroDTO.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .autor(libro.getAutor() != null ? libro.getAutor() : null)
                .isbn(libro.getIsbn())
                .fechaPublicacion(libro.getFechaPublicacion())
                .estado(libro.getEstado())
                .status(libro.isStatus())
                .build();
    }

    public Libro dtoToEntity(LibroDTO libroDto) {
        if (libroDto == null) {
            return null;
        }

        Libro libro = Libro.builder()
                .id(libroDto.getId())
                .titulo(libroDto.getTitulo())
                .isbn(libroDto.getIsbn())
                .fechaPublicacion(libroDto.getFechaPublicacion())
                .autor(libroDto.getAutor() != null ? libroDto.getAutor() : null)
                .estado(libroDto.getEstado())
                .status(libroDto.isStatus())
                .build();

        return libro;
    }
}
