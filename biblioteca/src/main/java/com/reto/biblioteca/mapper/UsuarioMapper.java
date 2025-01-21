package com.reto.biblioteca.mapper;

import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    // Método para convertir de Entidad a DTO
    public UsuarioDTO entityToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .documento(usuario.getDocumento())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .status(usuario.isStatus())
                .build();
    }

    // Método para convertir de DTO a Entidad
    public  Usuario dtoToEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }

        Usuario usuario = Usuario.builder()
                .id(usuarioDTO.getId())
                .nombre(usuarioDTO.getNombre())
                .documento(usuarioDTO.getDocumento())
                .email(usuarioDTO.getEmail())
                .telefono(usuarioDTO.getTelefono())
                .status(usuarioDTO.isStatus())
                .fechaNacimiento(usuarioDTO.getFechaNacimiento())
                .build();
        return usuario;
    }
}
