package com.reto.biblioteca.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reto.biblioteca.util.EstadoPrestamo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado; // Activo/Finalizado

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @JsonIgnoreProperties(value = {"prestamos","handler","hibernateLazyInitializer","exchange"}, allowSetters = true)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;

    @Column(name = "deleted_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date deletedAt;

    @Column(name = "status", nullable = false, columnDefinition = "NUMBER(1)")
    private boolean status;

    @PrePersist
    protected void onCreate(){
        this.createdAt = this.updatedAt = Date.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Date.from(Instant.now());
    }
}
