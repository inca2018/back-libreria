package com.reto.biblioteca.business.impl;

import com.reto.biblioteca.business.LibroService;
import com.reto.biblioteca.business.PrestamoService;
import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.entity.Prestamo;
import com.reto.biblioteca.dao.repository.AutorRepository;
import com.reto.biblioteca.dao.repository.LibroRepository;
import com.reto.biblioteca.dao.repository.PrestamoRepository;
import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.expose.dto.PrestamoDTO;
import com.reto.biblioteca.mapper.AutorMapper;
import com.reto.biblioteca.mapper.LibroMapper;
import com.reto.biblioteca.mapper.PrestamoMapper;
import com.reto.biblioteca.util.EstadoLibro;
import com.reto.biblioteca.util.EstadoPrestamo;
import com.reto.biblioteca.util.GenerateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoMapper mapper;

    @Autowired
    PrestamoRepository repository;

    @Autowired
    LibroRepository libroRepository;


    public PrestamoServiceImpl(PrestamoMapper mapper, PrestamoRepository repository,LibroRepository libroRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.libroRepository = libroRepository;
    }


    @Override
    public PrestamoDTO save(PrestamoDTO dto) {

        Prestamo entity = mapper.dtoToEntity(dto);
        entity.setStatus(true);
        entity.setEstado(EstadoPrestamo.ACTIVO);
        Prestamo entitySave = repository.save(entity);

        Optional<Libro> entityId = libroRepository.findById(dto.getLibro().getId());

        if (entityId.isPresent()) {
            entityId.get().setEstado(EstadoLibro.NO_DISPONIBLE);
            libroRepository.save(entityId.get());
        }
        return mapper.entityToDTO(entitySave);
    }


    @Override
    public void delete(Long id) {
        Optional<Prestamo> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Prestamo entity = entityId.get();

            Optional<Libro> libroRecuperado = libroRepository.findById(entity.getLibro().getId());

            if (libroRecuperado.isPresent()) {
                libroRecuperado.get().setEstado(EstadoLibro.DISPONIBLE);
                libroRepository.save(libroRecuperado.get());
            }

            repository.delete(entity);
        } else {
            GenerateException.lanzarExcepcion("libro","El prestamo proporcionado no existe.");
        }
    }

    @Override
    public ResponseEntity<Page<PrestamoDTO>> index(Pageable pageable, String search) {

        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Prestamo> customerPage = repository.findByLibroTituloContaining(search, pageable);
        Page<PrestamoDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public PrestamoDTO finalizar(Long id) {
        Optional<Prestamo> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Prestamo entity = entityId.get();

            Optional<Libro> libroRecuperado = libroRepository.findById(entity.getLibro().getId());

            if (libroRecuperado.isPresent()) {
                libroRecuperado.get().setEstado(EstadoLibro.DISPONIBLE);
                libroRepository.save(libroRecuperado.get());
            }

            entity.setEstado(EstadoPrestamo.FINALIZADO);
            Prestamo updatedEntity = repository.save(entity);
            return mapper.entityToDTO(updatedEntity);
        } else {
            GenerateException.lanzarExcepcion("libro","El prestamo no se encuentra registrado.");
            return null;
        }
    }


}
