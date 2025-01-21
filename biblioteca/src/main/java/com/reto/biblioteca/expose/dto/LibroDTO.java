package com.reto.biblioteca.expose.dto;

import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.util.EstadoLibro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDTO {
    private Long id;

    @NotNull(message = "El Titulo es un campo obligatorio.")
    @NotBlank(message = "El Titulo no debe estar en blanco.")
    private String titulo;

    private Autor autor;

    @NotNull(message = "El ISBN es un campo obligatorio.")
    @NotBlank(message = "El ISBN no debe estar en blanco.")
    private String isbn;

    private LocalDate fechaPublicacion;

    private EstadoLibro estado;

    private boolean status;
}
