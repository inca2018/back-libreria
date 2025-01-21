package com.reto.biblioteca.business.impl;

import com.reto.biblioteca.business.LibroService;
import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Libro;
import com.reto.biblioteca.dao.repository.AutorRepository;
import com.reto.biblioteca.dao.repository.LibroRepository;
import com.reto.biblioteca.dao.repository.PrestamoRepository;
import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.mapper.AutorMapper;
import com.reto.biblioteca.mapper.LibroMapper;
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
public class LibroServiceImpl implements LibroService {

    @Autowired
    LibroMapper mapper;

    @Autowired
    AutorMapper autorMapper;

    @Autowired
    LibroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    PrestamoRepository prestamoRepository;

    public LibroServiceImpl(LibroMapper mapper, LibroRepository repository, AutorRepository autorRepository,PrestamoRepository prestamoRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.autorRepository = autorRepository ;
        this.prestamoRepository = prestamoRepository ;
    }

    @Override
    public List<LibroDTO> findAll() {
        List<Libro> providerEntities = repository.findAll();
        return providerEntities.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LibroDTO> findById(Long id) {
        Optional<Libro> entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("libro","Libro no encontrado");
            return null;
        }
    }

    @Override
    public LibroDTO save(LibroDTO providerDto) {

        if (repository.existsByIsbn(providerDto.getIsbn())) {
            GenerateException.lanzarExcepcion("isbn","El ISBN ya se encuentra registrado.");
            return null;

        }
        if (repository.existsByTitulo(providerDto.getTitulo())) {
            GenerateException.lanzarExcepcion("titulo","El libro ya se encuentra registrado.");
            return null;

        }

        Libro entity = mapper.dtoToEntity(providerDto);
        entity.setStatus(true);
        entity.setEstado(EstadoLibro.DISPONIBLE);
        Libro entitySave = repository.save(entity);
        return mapper.entityToDTO(entitySave);
    }


    @Override
    public LibroDTO update(Long id, LibroDTO providerDto) {

        Optional<Libro> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            if (repository.findByIsbnAndIdNot(providerDto.getIsbn(),id)!=null) {
                GenerateException.lanzarExcepcion("isbn","El ISBN ya se encuentra registrado.");
                return null;

            }

            if (repository.findByTituloAndIdNot(providerDto.getTitulo(),id) != null) {
                GenerateException.lanzarExcepcion("titulo","El titulo ya se encuentra registrado.");
                return null;

            }
            providerDto.setId(id);
            Libro entity = mapper.dtoToEntity(providerDto);
            Libro entityUpdate = repository.save(entity);
            return mapper.entityToDTO(entityUpdate);
        }else{
            GenerateException.lanzarExcepcion("libro","El libro no se encuentra registrado.");
            return null;

        }
    }
    @Override
    public void delete(Long id) {
        Optional<Libro> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Libro libro = entityId.get();

            boolean tienePrestamoActivo = prestamoRepository.existsByLibroIdAndEstado(id, EstadoPrestamo.ACTIVO);

            if (tienePrestamoActivo) {
                GenerateException.lanzarExcepcion("libro",
                        "No se puede eliminar el libro porque tiene préstamos activos.");
            }

            // Si no hay préstamos activos, se elimina el libro
            repository.delete(libro);
        } else {
            GenerateException.lanzarExcepcion("libro",
                    "El libro con el ID proporcionado no existe.");
        }
    }

    @Override
    public ResponseEntity<Page<LibroDTO>> index(Pageable pageable, String search) {

        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Libro> customerPage = repository.findByTituloContaining(search, pageable);
        Page<LibroDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public ResponseEntity<Page<LibroDTO>> indexEnabled(Pageable pageable, String search) {
        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Libro> customerPage = repository.findByTituloContainingAndStatusIsTrue(search, pageable);
        Page<LibroDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public ResponseEntity<Page<LibroDTO>> indexActivo(Pageable pageable, String search) {
        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Libro> customerPage = repository.findByTituloContainingAndEstadoAndStatusTrue(search, EstadoLibro.DISPONIBLE, pageable);
        Page<LibroDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public LibroDTO enable(Long id) {
        return changeStatus(id, true);
    }

    @Override
    public LibroDTO disable(Long id) {
        return changeStatus(id, false);
    }

    @Override
    public Optional<LibroDTO> getByTitulo(String nombre) {
        Optional<Libro> entity = repository.findByTitulo(nombre);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("libro","El libro no se encuentra registrado.");
            return null;

        }
    }

    private LibroDTO changeStatus(Long id, boolean status) {
        Optional<Libro> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Libro entity = entityId.get();
            entity.setStatus(status);
            Libro updatedEntity = repository.save(entity);
            return mapper.entityToDTO(updatedEntity);
        } else {
            GenerateException.lanzarExcepcion("libro","El libro no se encuentra registrado.");
            return null;

        }
    }

    @Transactional
    @Override
    public LibroDTO agregarAutor(Long libroId, Long autorId) {
        Optional<Libro> libroOpt = repository.findById(libroId);
        Optional<Autor> autorOpt = autorRepository.findById(autorId);

        if (libroOpt.isEmpty()) {
            GenerateException.lanzarExcepcion("libro", "Libro no encontrado.");
        }

        if (autorOpt.isEmpty()) {
            GenerateException.lanzarExcepcion("autor", "Autor no encontrado.");
        }

        Libro libro = libroOpt.get();
        Autor autor = autorOpt.get();

        libro.setAutor(autor);  // Asignamos el autor al libro
        Libro libroUpdated = repository.save(libro);  // Guardamos el libro con su autor
        return mapper.entityToDTO(libroUpdated);
    }



    @Transactional
    @Override
    public LibroDTO eliminarAutor(Long libroId, Long autorId) {
        Optional<Libro> libroOpt = repository.findById(libroId);
        Optional<Autor> autorOpt = autorRepository.findById(autorId);

        if (libroOpt.isEmpty()) {
            GenerateException.lanzarExcepcion("libro", "Libro no encontrado.");
        }

        if (autorOpt.isEmpty()) {
            GenerateException.lanzarExcepcion("autor", "Autor no encontrado.");
        }

        Libro libro = libroOpt.get();
        Autor autor = autorOpt.get();

        // Si el autor asociado al libro es el mismo que el que estamos eliminando, lo eliminamos
        if (libro.getAutor().equals(autor)) {
            libro.setAutor(null);  // Elimina la relación
            Libro libroUpdated = repository.save(libro);  // Guardamos el libro actualizado
            return mapper.entityToDTO(libroUpdated);
        } else {
            GenerateException.lanzarExcepcion("autor", "Este autor no está asociado al libro.");
            return null;
        }
    }


    @Override
    public AutorDTO listarAutores(Long libroId) {
        Optional<Libro> libroOpt = repository.findById(libroId);

        if (libroOpt.isEmpty()) {
            GenerateException.lanzarExcepcion("libro", "Libro no encontrado.");
        }

        Libro libro = libroOpt.get();
        // Suponiendo que el libro tiene un solo autor
        Autor autor = libro.getAutor();
        return autorMapper.entityToDTO(autor);  // Devolvemos el autor del libro
    }


}
