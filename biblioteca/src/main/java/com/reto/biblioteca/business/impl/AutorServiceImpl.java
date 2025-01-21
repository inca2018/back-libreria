package com.reto.biblioteca.business.impl;

import com.reto.biblioteca.business.AutorService;
import com.reto.biblioteca.dao.entity.Autor;
import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.dao.repository.AutorRepository;
import com.reto.biblioteca.expose.dto.AutorDTO;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import com.reto.biblioteca.mapper.AutorMapper;
import com.reto.biblioteca.util.GenerateException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    AutorMapper mapper;

    @Autowired
    AutorRepository repository;

    @Autowired
    private EntityManager entityManager;

    public AutorServiceImpl(AutorMapper mapper, AutorRepository repository,EntityManager entityManager) {
        this.mapper = mapper;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<AutorDTO> findAll() {
        List<Autor> providerEntities = repository.findAll();
        return providerEntities.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AutorDTO> findById(Long id) {
        Optional<Autor> entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("autor","Autor no encontrado");
            return null;
        }
    }

    @Override
    public AutorDTO save(AutorDTO providerDto) {

        if (repository.existsByNombre(providerDto.getNombre())) {
            GenerateException.lanzarExcepcion("nombre","El autor ya se encuentra registrado.");
            return null;

        }

        Autor entity = mapper.dtoToEntity(providerDto);
        entity.setStatus(true);
        Autor entitySave = repository.save(entity);
        return mapper.entityToDTO(entitySave);
    }


    @Override
    public AutorDTO update(Long id, AutorDTO providerDto) {

        Optional<Autor> entityId = repository.findById(id);
        if (entityId.isPresent()) {


            if (repository.findByNombreAndIdNot(providerDto.getNombre(),id) != null) {
                GenerateException.lanzarExcepcion("nombre","El nombre ya se encuentra registrado.");
                return null;

            }
            providerDto.setId(id);
            Autor entity = mapper.dtoToEntity(providerDto);
            Autor entityUpdate = repository.save(entity);
            return mapper.entityToDTO(entityUpdate);
        }else{
            GenerateException.lanzarExcepcion("autor","El Autor no se encuentra registrado.");
            return null;

        }
    }

    @Override
    public void delete(Long id) {
        Optional<Autor> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Autor entity = entityId.get();
            repository.delete(entity);
        } else {
            GenerateException.lanzarExcepcion("autor","El Autor con el ID proporcionado no existe.");
        }
    }

    @Override
    public ResponseEntity<Page<AutorDTO>> index(Pageable pageable, String search) {

        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Autor> customerPage = repository.findByNombreContaining(search, pageable);
        Page<AutorDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public ResponseEntity<Page<AutorDTO>> indexEnabled(Pageable pageable, String search) {
        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Autor> customerPage = repository.findByNombreContainingAndStatusIsTrue(search, pageable);
        Page<AutorDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public AutorDTO enable(Long id) {
        return changeStatus(id, true);
    }

    @Override
    public AutorDTO disable(Long id) {
        return changeStatus(id, false);
    }

    @Override
    public Optional<AutorDTO> getByNombre(String nombre) {
        Optional<Autor> entity = repository.findByNombre(nombre);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("autor","El Autor no se encuentra registrado.");
            return null;

        }
    }

    private AutorDTO changeStatus(Long id, boolean status) {
        Optional<Autor> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Autor entity = entityId.get();
            entity.setStatus(status);
            Autor updatedEntity = repository.save(entity);
            return mapper.entityToDTO(updatedEntity);
        } else {
            GenerateException.lanzarExcepcion("autor","El Autor no se encuentra registrado.");
            return null;

        }
    }

    @Override
    public List<AutorDTO> search(String nombre, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Autor> query = cb.createQuery(Autor.class);
        Root<Autor> root = query.from(Autor.class);

        List<Predicate> predicates = new ArrayList<>();

        // Agregar condiciones dinámicas a la consulta
        if (!nombre.isEmpty()) {
            predicates.add(cb.like(root.get("nombre"), "%" + nombre + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Implementar paginación
        TypedQuery<Autor> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Autor> usuarios = typedQuery.getResultList();
        return usuarios.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
