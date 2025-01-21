package com.reto.biblioteca.expose;

import com.reto.biblioteca.business.AutorService;
import com.reto.biblioteca.exception.BaseException;
import com.reto.biblioteca.expose.dto.AutorDTO;
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
@RequestMapping(path = "/autor")
@Slf4j
@RequiredArgsConstructor
public class AutorController {

    private static final Logger logger = LoggerFactory.getLogger(AutorController.class);
    private final AutorService service;

    @GetMapping
    public ResponseEntity<?> index(
            Pageable pageable,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /autor", "search=" + search);


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
        logRequest("GET /autor/enabled", "search=" + search);

        try {
            return logAndRespond(HttpStatus.OK, service.indexEnabled(pageable, search));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody(required = false) @Valid AutorDTO dto,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("POST /autor", dto.toString());

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
        logRequest("GET /autor",null);
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
            @RequestBody(required = false) @Valid AutorDTO dto,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /autor/{id}", dto.toString());

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
        logRequest("DELETE /autor/{id}",id.toString());

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
        logRequest("PUT /autor/{id}/enable",id.toString());

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
        logRequest("PUT /autor/{id}/disable",id.toString());

        try {
            return logAndRespond(HttpStatus.OK, service.disable(id));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @GetMapping("/{nombre}/by-nombre")
    public ResponseEntity<?> getByNombre(
            @PathVariable("nombre") String nombre
    ) {
        logRequest("GET /autor/{nombre}/by-nombre",nombre);

        try {
            return logAndRespond(HttpStatus.OK, service.getByNombre(nombre));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // API APLICANDO CRITERIA
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(name = "nombre", defaultValue = "") String nombre,
            Pageable pageable,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("GET /usuario/search", "nombre=" + nombre);

        try {
            return logAndRespond(HttpStatus.OK, service.search(nombre, pageable));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}