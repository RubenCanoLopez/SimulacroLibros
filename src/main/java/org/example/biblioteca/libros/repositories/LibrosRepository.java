package org.example.biblioteca.libros.repositories;

import org.example.biblioteca.libros.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * TAREA 2 · El repositorio de libros. Ahora es una interfaz vacia:
 * todavia no es un repositorio de Spring Data.
 * Lo que tiene que tener esta en el enunciado (TAREA 2).
 */
public interface LibrosRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro> {

    Optional<Libro> findByIdAndDeletedFalse (Long id);
    boolean existsByIsbnIgnoreCase(String isbn);

    @Override
    Page<Libro> findAll(Specification<Libro> spec, Pageable pageable);


}
