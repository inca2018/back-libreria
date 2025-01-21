package com.reto.biblioteca.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<Prestamo> prestamos;

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
