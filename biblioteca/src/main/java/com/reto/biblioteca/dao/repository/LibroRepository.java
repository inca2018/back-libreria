package com.reto.biblioteca.dao.repository;

import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.util.EstadoLibro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Page<Libro> findByTituloContaining(String search, Pageable pageable);

    Page<Libro> findByTituloContainingAndStatusIsTrue(String search, Pageable pageable);

    Page<Libro> findByTituloContainingAndEstadoAndStatusTrue(String search, EstadoLibro estado, Pageable pageable);

    Libro findByIsbnAndIdNot(String documento, Long id);

    Libro findByTituloAndIdNot(String nombre, Long id);

    Optional<Libro> findByTitulo(String nombre);

    boolean existsByIsbn(String documento);

    boolean existsByTitulo(String nombre);
}
