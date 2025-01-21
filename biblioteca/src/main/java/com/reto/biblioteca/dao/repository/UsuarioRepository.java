package com.reto.biblioteca.dao.repository;

import com.reto.biblioteca.dao.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByNombreContaining(String search, Pageable pageable);

    Page<Usuario> findByNombreContainingAndStatusIsTrue(String search, Pageable pageable);

    Usuario findByDocumentoAndIdNot(String documento, Long id);

    Usuario findByNombreAndIdNot(String nombre, Long id);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByDocumento(String documento);

    boolean existsByNombre(String nombre);
}
