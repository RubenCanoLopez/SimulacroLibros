package org.example.biblioteca.libros.dto;

import jakarta.validation.constraints.Size;

public record StockRequest(

        @Size(min = -50, max = 50)
        Integer cantidad
)
{}
