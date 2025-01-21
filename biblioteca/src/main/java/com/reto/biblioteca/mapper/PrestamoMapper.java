package com.reto.biblioteca.mapper;

import com.reto.biblioteca.dao.entity.Prestamo;
import com.reto.biblioteca.expose.dto.PrestamoDTO;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {

    public PrestamoDTO entityToDTO(Prestamo prestamo) {
        if (prestamo == null) {
            return null;
        }

        return PrestamoDTO.builder()
                .id(prestamo.getId())
                .fechaPrestamo(prestamo.getFechaPrestamo())
                .fechaDevolucion(prestamo.getFechaDevolucion())
                .estado(prestamo.getEstado())
                .libro(prestamo.getLibro() != null ? prestamo.getLibro() : null)
                .usuario(prestamo.getUsuario() != null ? prestamo.getUsuario() : null)
                .build();
    }

    public Prestamo dtoToEntity(PrestamoDTO prestamoDTO) {
        if (prestamoDTO == null) {
            return null;
        }

        return Prestamo.builder()
                .id(prestamoDTO.getId())
                .fechaPrestamo(prestamoDTO.getFechaPrestamo())
                .fechaDevolucion(prestamoDTO.getFechaDevolucion())
                .estado(prestamoDTO.getEstado())
                .libro(prestamoDTO.getLibro() != null ? prestamoDTO.getLibro() : null)
                .usuario(prestamoDTO.getUsuario() != null ? prestamoDTO.getUsuario() : null)
                .build();
    }
}
