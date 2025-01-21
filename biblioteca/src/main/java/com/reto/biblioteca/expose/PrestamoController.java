package com.reto.biblioteca.expose;

import com.reto.biblioteca.business.PrestamoService;
import com.reto.biblioteca.business.UsuarioService;
import com.reto.biblioteca.exception.BaseException;
import com.reto.biblioteca.expose.dto.PrestamoDTO;
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
@RequestMapping(path = "/prestamo")
@Slf4j
@RequiredArgsConstructor
public class PrestamoController {

    private static final Logger logger = LoggerFactory.getLogger(PrestamoController.class);
    private final PrestamoService service;

    @GetMapping
    public ResponseEntity<?> index(
            Pageable pageable,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("GET /prestamo", "search=" + search);


        try {
            return logAndRespond(HttpStatus.OK, service.index(pageable, search));
        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody(required = false) @Valid PrestamoDTO dto,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    )
    {
        logRequest("POST /prestamo", dto.toString());

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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("DELETE /prestamo/{id}",id.toString());

        try {

            service.delete(id);
            return logAndRespond(HttpStatus.OK, "");

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> enable(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Authorization", defaultValue = "") String accessToken,
            @RequestHeader(name = "Requestor", defaultValue = "") String requestor
    ) {
        logRequest("PUT /prestamo/{id}/enable",id.toString());

        try {
            return logAndRespond(HttpStatus.OK, service.finalizar(id));

        } catch (BaseException e) {
            return logAndRespond(HttpStatus.UNPROCESSABLE_ENTITY, e.getErrors());
        } catch (Exception e) {
            return logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}