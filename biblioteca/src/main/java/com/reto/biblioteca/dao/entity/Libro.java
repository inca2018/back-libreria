package com.reto.biblioteca.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reto.biblioteca.util.EstadoLibro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @JsonIgnoreProperties(value = {"libros","handler","hibernateLazyInitializer","exchange"}, allowSetters = true)
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String isbn;
    private LocalDate fechaPublicacion;
    @Enumerated(EnumType.STRING)
    private EstadoLibro estado;


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
