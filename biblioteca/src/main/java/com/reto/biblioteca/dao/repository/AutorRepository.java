package com.reto.biblioteca.dao.repository;

import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Page<Autor> findByNombreContaining(String search, Pageable pageable);

    Page<Autor> findByNombreContainingAndStatusIsTrue(String search, Pageable pageable);

    Autor findByNombreAndIdNot(String nombre, Long id);

    Optional<Autor> findByNombre(String nombre);


    boolean existsByNombre(String nombre);

}
