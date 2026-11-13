package org.example.biblioteca.libros.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * TAREA 3.a · Lo que ENTRA en el POST y en el PUT.
 * Los campos ya estan (el mapper los usa). Añade las validaciones
 * de la tabla del enunciado, con mensajes propios.
 */
public record LibroRequest(

        @NotBlank(message = "El nombre del título es oligatorio")
        @Size(max = 200)
        String titulo,

        @NotBlank(message = "El ISBN debe ser obligatorio y cumplir con el patrón de este")
        @Pattern(regexp = "97[89]-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d")
        String isbn,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor de 0")
        @Digits(integer = 10, fraction = 2)
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El año de publicación es obligatorio")
        @Size(min = 1450, max = 2100)
        Integer anioPublicacion,

        @NotNull(message = "El id del autor es obligatorio")
        Long autorId
) {}
