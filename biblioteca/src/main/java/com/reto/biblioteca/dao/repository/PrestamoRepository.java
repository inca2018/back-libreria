package com.reto.biblioteca.dao.repository;

import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.entity.Prestamo;
import com.reto.biblioteca.util.EstadoPrestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Page<Prestamo> findByLibroTituloContaining(String search, Pageable pageable);

    boolean existsByLibroIdAndEstado(Long libroId, EstadoPrestamo estado);

}
