package org.example.biblioteca.libros.exceptions;

public class LibroSinStockException extends RuntimeException {
    
    public LibroSinStockException(String message) {
        super(message);
    }
}
