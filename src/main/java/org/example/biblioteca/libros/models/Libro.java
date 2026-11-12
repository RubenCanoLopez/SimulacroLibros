package org.example.biblioteca.libros.models;

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


@Getter
@Setter
@NoArgsConstructor
public class Libro {

    private Long id;

    private String titulo;

    private String isbn;

    private BigDecimal precio;

    private Integer stock;

    private Integer anioPublicacion;

    private Autor autor;

    private boolean deleted = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
