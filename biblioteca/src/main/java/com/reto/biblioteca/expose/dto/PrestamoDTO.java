package com.reto.biblioteca.expose.dto;

import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.util.EstadoPrestamo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoDTO {

    private Long id;
    @NotNull(message = "La fecha de préstamo no puede ser nula")
    @FutureOrPresent(message = "La fecha de préstamo debe ser en el presente o futuro")
    private LocalDate fechaPrestamo;
    @NotNull(message = "La fecha de devolución no puede ser nula")
    @FutureOrPresent(message = "La fecha de devolución debe ser en el presente o futuro")
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;
    @NotNull(message = "El libro no puede ser nulo")
    private Libro libro;
    @NotNull(message = "El usuario no puede ser nulo")
    private Usuario usuario;

}
