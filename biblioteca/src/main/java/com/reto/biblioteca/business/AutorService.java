package com.reto.biblioteca.business;

import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AutorService {

    List<AutorDTO> findAll();

    Optional<AutorDTO> findById(Long id);

    AutorDTO save(AutorDTO dto);

    AutorDTO update(Long id, AutorDTO dto);

    void delete(Long id);

    ResponseEntity<Page<AutorDTO>> index(Pageable pageable, String search);

    ResponseEntity<Page<AutorDTO>> indexEnabled(Pageable pageable, String search);

    AutorDTO enable(Long id);

    AutorDTO disable(Long id);

    Optional<AutorDTO> getByNombre(String nombre);

    List<AutorDTO> search(String nombre, Pageable pageable);

}
