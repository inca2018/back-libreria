package com.reto.biblioteca.business.impl;

import com.reto.biblioteca.business.UsuarioService;
import com.reto.biblioteca.dao.entity.Usuario;
import com.reto.biblioteca.dao.repository.UsuarioRepository;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import com.reto.biblioteca.mapper.UsuarioMapper;
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
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioMapper mapper;

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioMapper mapper, UsuarioRepository repository,EntityManager entityManager) {
        this.mapper = mapper;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        List<Usuario> providerEntities = repository.findAll();
        return providerEntities.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDTO> findById(Long id) {
        Optional<Usuario> entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("usuario","Usuario no encontrado");
            return null;
        }
    }

    @Override
    public UsuarioDTO save(UsuarioDTO providerDto) {

        if (repository.existsByDocumento(providerDto.getDocumento())) {
            GenerateException.lanzarExcepcion("documento","El documento ya se encuentra registrado.");
            return null;

        }
        if (repository.existsByNombre(providerDto.getNombre())) {
            GenerateException.lanzarExcepcion("nombre","El usuario ya se encuentra registrado.");
            return null;

        }

        Usuario entity = mapper.dtoToEntity(providerDto);
        entity.setStatus(true);
        Usuario entitySave = repository.save(entity);
        return mapper.entityToDTO(entitySave);
    }


    @Override
    public UsuarioDTO update(Long id, UsuarioDTO providerDto) {

        Optional<Usuario> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            if (repository.findByDocumentoAndIdNot(providerDto.getDocumento(),id)!=null) {
                GenerateException.lanzarExcepcion("documento","El documento ya se encuentra registrado.");
                return null;

            }

            if (repository.findByNombreAndIdNot(providerDto.getNombre(),id) != null) {
                GenerateException.lanzarExcepcion("nombre","El nombre ya se encuentra registrado.");
                return null;

            }
            providerDto.setId(id);
            Usuario entity = mapper.dtoToEntity(providerDto);
            Usuario entityUpdate = repository.save(entity);
            return mapper.entityToDTO(entityUpdate);
        }else{
            GenerateException.lanzarExcepcion("usuario","El usuario no se encuentra registrado.");
            return null;

        }
    }

    @Override
    public void delete(Long id) {
        Optional<Usuario> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Usuario entity = entityId.get();
            repository.delete(entity);
        } else {
            GenerateException.lanzarExcepcion("usuario","El usuario con el ID proporcionado no existe.");
        }
    }

    @Override
    public ResponseEntity<Page<UsuarioDTO>> index(Pageable pageable, String search) {

        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Usuario> customerPage = repository.findByNombreContaining(search, pageable);
        Page<UsuarioDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public ResponseEntity<Page<UsuarioDTO>> indexEnabled(Pageable pageable, String search) {
        if((pageable.getPageNumber() - 1) < 0){
            pageable = PageRequest.of(0, 10);
        }else{
            pageable = PageRequest.of(pageable.getPageNumber() - 1, 10);
        }

        Page<Usuario> customerPage = repository.findByNombreContainingAndStatusIsTrue(search, pageable);
        Page<UsuarioDTO> customerDtoPage = customerPage.map(mapper::entityToDTO);

        return ResponseEntity.ok(customerDtoPage);
    }

    @Override
    public UsuarioDTO enable(Long id) {
        return changeStatus(id, true);
    }

    @Override
    public UsuarioDTO disable(Long id) {
        return changeStatus(id, false);
    }

    @Override
    public Optional<UsuarioDTO> getByNombre(String nombre) {
        Optional<Usuario> entity = repository.findByNombre(nombre);
        if (entity.isPresent()) {
            return entity.map(mapper::entityToDTO);
        }else{
            GenerateException.lanzarExcepcion("usuario","El usuario no se encuentra registrado.");
            return null;

        }
    }

    private UsuarioDTO changeStatus(Long id, boolean status) {
        Optional<Usuario> entityId = repository.findById(id);
        if (entityId.isPresent()) {
            Usuario entity = entityId.get();
            entity.setStatus(status);
            Usuario updatedEntity = repository.save(entity);
            return mapper.entityToDTO(updatedEntity);
        } else {
            GenerateException.lanzarExcepcion("usuario","El usuario no se encuentra registrado.");
            return null;

        }
    }

    @Override
    public List<UsuarioDTO> search(String nombre, String email, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
        Root<Usuario> root = query.from(Usuario.class);

        List<Predicate> predicates = new ArrayList<>();

        // Agregar condiciones dinámicas a la consulta
        if (!nombre.isEmpty()) {
            predicates.add(cb.like(root.get("nombre"), "%" + nombre + "%"));
        }
        if (!email.isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + email + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Implementar paginación
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Usuario> usuarios = typedQuery.getResultList();
        return usuarios.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }


}
