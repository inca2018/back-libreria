    package com.reto.biblioteca.dao.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import java.sql.Date;
    import java.time.Instant;
    import java.time.LocalDate;
    import java.util.Set;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    @Table(name = "autor")
    public class Autor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nombre;
        private String nacionalidad;
        private LocalDate fechaNacimiento;

        @JsonIgnore
        @OneToMany(mappedBy = "autor") // "autor" es el nombre del campo en la clase Libro
        private Set<Libro> libros;

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
