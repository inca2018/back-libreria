package com.reto.biblioteca.business;

import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.expose.dto.PrestamoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {

    PrestamoDTO save(PrestamoDTO dto);

    void delete(Long id);

    ResponseEntity<Page<PrestamoDTO>> index(Pageable pageable, String search);

    PrestamoDTO finalizar(Long id);
}
