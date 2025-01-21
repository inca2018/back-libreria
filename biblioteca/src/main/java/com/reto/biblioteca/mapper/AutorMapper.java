package com.reto.biblioteca.mapper;

import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AutorMapper {

    public AutorDTO entityToDTO(Autor autor) {
        if (autor == null) {
            return null;
        }

        return AutorDTO.builder()
                .id(autor.getId())
                .nombre(autor.getNombre())
                .nacionalidad(autor.getNacionalidad())
                .fechaNacimiento(autor.getFechaNacimiento())
                .status(autor.isStatus())
                .build();
    }

    public  Autor dtoToEntity(AutorDTO autorDTO) {
        if (autorDTO == null) {
            return null;
        }

        Autor usuario = Autor.builder()
                .id(autorDTO.getId())
                .nombre(autorDTO.getNombre())
                .nacionalidad(autorDTO.getNacionalidad())
                .fechaNacimiento(autorDTO.getFechaNacimiento())
                .status(autorDTO.isStatus())
                .build();

        return usuario;
    }
}
