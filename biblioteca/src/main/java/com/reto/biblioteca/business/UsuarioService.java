package com.reto.biblioteca.business;

import com.reto.biblioteca.expose.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<UsuarioDTO> findAll();

    Optional<UsuarioDTO> findById(Long id);

    UsuarioDTO save(UsuarioDTO dto);

    UsuarioDTO update(Long id, UsuarioDTO dto);

    void delete(Long id);

    ResponseEntity<Page<UsuarioDTO>> index(Pageable pageable, String search);

    ResponseEntity<Page<UsuarioDTO>> indexEnabled(Pageable pageable, String search);

    UsuarioDTO enable(Long id);

    UsuarioDTO disable(Long id);

    Optional<UsuarioDTO> getByNombre(String nombre);

    List<UsuarioDTO> search(String nombre, String email, Pageable pageable);
}
