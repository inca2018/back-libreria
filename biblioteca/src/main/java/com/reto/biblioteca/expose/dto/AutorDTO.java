package com.reto.biblioteca.expose.dto;

import com.reto.biblioteca.dao.entity.Libro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDTO {
    private Long id;
    @NotNull(message = "El Nombre es un campo obligatorio.")
    @NotBlank(message = "El Nombre no debe estar en blanco.")
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private Set<Libro> libros;
    private boolean status;
}
