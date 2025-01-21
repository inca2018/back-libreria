package com.reto.biblioteca.expose.dto;

import jakarta.validation.constraints.Email;
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
public class UsuarioDTO {
    private Long id;
    @NotNull(message = "El Nombre es un campo obligatorio.")
    @NotBlank(message = "El Nombre no debe estar en blanco.")
    private String nombre;
    @NotNull(message = "El Documento es un campo obligatorio.")
    @NotBlank(message = "El Documento no debe estar en blanco.")
    private String documento;
    @NotNull(message = "El email es un campo obligatorio.")
    @NotBlank(message = "El email no debe estar en blanco.")
    @Email(message = "El email es inválido")
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Set<Long> prestamos;
    private boolean status;
}