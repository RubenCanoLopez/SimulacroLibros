package org.example.biblioteca.libros.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StockRequest(

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = -50, message = "La cantidad mínima es -50")
        @Max(value = 50, message = "La cantidad máxima es 50")
        Integer cantidad
)
{}
