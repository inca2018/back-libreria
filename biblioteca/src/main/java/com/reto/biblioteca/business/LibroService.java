package com.reto.biblioteca.business;

import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    List<LibroDTO> findAll();

    Optional<LibroDTO> findById(Long id);

    LibroDTO save(LibroDTO dto);

    LibroDTO update(Long id, LibroDTO dto);

    void delete(Long id);

    ResponseEntity<Page<LibroDTO>> index(Pageable pageable, String search);

    ResponseEntity<Page<LibroDTO>> indexEnabled(Pageable pageable, String search);

    ResponseEntity<Page<LibroDTO>> indexActivo(Pageable pageable, String search);


    LibroDTO enable(Long id);

    LibroDTO disable(Long id);

    Optional<LibroDTO> getByTitulo(String nombre);



    LibroDTO agregarAutor(Long libroId, Long autorId);

    LibroDTO eliminarAutor(Long libroId, Long autorId);

    AutorDTO listarAutores(Long libroId);
}
