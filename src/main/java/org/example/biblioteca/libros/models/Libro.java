package org.example.biblioteca.libros.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.biblioteca.autores.models.Autor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TAREA 1 · Convierte esta clase en una ENTIDAD JPA que genere exactamente
 * la tabla "libros" de la TAREA 1 del enunciado.
 *
 * Los campos, los getters/setters (Lombok) y el constructor vacio ya estan:
 * NO cambies sus nombres ni sus tipos, porque el mapper los usa.
 * Todavia NO es una entidad: por eso el proyecto arranca sin ella.
 */

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 10, precision = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer anioPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
