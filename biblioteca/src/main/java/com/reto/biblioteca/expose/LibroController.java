package com.reto.biblioteca.expose;

import com.reto.biblioteca.business.LibroService;
import com.reto.biblioteca.business.UsuarioService;
import com.reto.biblioteca.exception.BaseException;
import com.reto.biblioteca.expose.dto.LibroDTO;
import com.reto.biblioteca.expose.dto.UsuarioDTO;
import com.reto.biblioteca.util.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.reto.biblioteca.util.Util.*;

@RestController
@Validated
@RequestMapping(path = "/libro")
@Slf4j
@RequiredArgsConstructor
public class LibroController {

    private static final Logger logger = LoggerFactory.getLogger(LibroController.class);
    private final LibroService service;

    @GetMapping
    public ResponseEntity<?> index(
            Pageable pageable,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /libro", "search=" + search);


        try {
            return logAndRespond(HttpStatus.OK, service.index(pageable, search));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/enabled")
    public ResponseEntity<?> indexEnable(
            Pageable pageable,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /libro/enabled", "search=" + search);

        try {
            return logAndRespond(HttpStatus.OK, service.indexEnabled(pageable, search));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<?> indexActivos(
            Pageable pageable,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /libro/activos", "search=" + search);

        try {
            return logAndRespond(HttpStatus.OK, service.indexActivo(pageable, search));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody(required = false) @Valid LibroDTO dto,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("POST /libro", dto.toString());

        try {

            if (isInvalidRequest(dto)) {
                return logAndRespond(HttpStatus.BAD_REQUEST, Constant.BAD_REQUEST_MESSAGE);
            }
            return logAndRespond(HttpStatus.OK, service.save(dto));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /libro",null);
        try {

            return service.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestBody(required = false) @Valid LibroDTO dto,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /libro/{id}", dto.toString());

        try {

            if (isInvalidRequest(dto)) {
                return logAndRespond(HttpStatus.BAD_REQUEST, Constant.BAD_REQUEST_MESSAGE);
            }
            return logAndRespond(HttpStatus.OK, service.update(id,dto));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("DELETE /libro/{id}",id.toString());

        try {

            service.delete(id);
            return logAndRespond(HttpStatus.OK, "");

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<?> enable(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /libro/{id}/enable",id.toString());

        try {
            return logAndRespond(HttpStatus.OK, service.enable(id));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disable(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /libro/{id}/disable",id.toString());

        try {
            return logAndRespond(HttpStatus.OK, service.disable(id));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @GetMapping("/{titulo}/by-titulo")
    public ResponseEntity<?> getByNombre(
            @PathVariable("titulo") String nombre
    ) {
        logRequest("GET /libro/{nombre}/by-titulo",nombre);

        try {
            return logAndRespond(HttpStatus.OK, service.getByTitulo(nombre));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }



    @PutMapping("/{id}/agregar-autor/{autorId}")
    public ResponseEntity<?> agregarAutor(
            @PathVariable("id") Long id,
            @PathVariable("autorId") Long autorId,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /libro/{id}/agregar-autor", "autorId=" + autorId);

        try {
            return logAndRespond(HttpStatus.OK, service.agregarAutor(id, autorId));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Eliminar autor de libro
    @PutMapping("/{id}/eliminar-autor/{autorId}")
    public ResponseEntity<?> eliminarAutor(
            @PathVariable("id") Long id,
            @PathVariable("autorId") Long autorId,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /libro/{id}/eliminar-autor", "autorId=" + autorId);

        try {
            return logAndRespond(HttpStatus.OK, service.eliminarAutor(id, autorId));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Listar autores de un libro
    @GetMapping("/{id}/autores")
    public ResponseEntity<?> listarAutores(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("GET /libro/{id}/autores", "id=" + id);

        try {
            return logAndRespond(HttpStatus.OK, service.listarAutores(id));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}