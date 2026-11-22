package org.example.biblioteca.libros.services;

import org.example.biblioteca.autores.models.Autor;
import org.example.biblioteca.autores.repositories.AutoresRepository;
import org.example.biblioteca.libros.dto.LibroRequest;
import org.example.biblioteca.libros.dto.LibroResponse;
import org.example.biblioteca.libros.exceptions.LibroBadRequestException;
import org.example.biblioteca.libros.exceptions.LibroConflictException;
import org.example.biblioteca.libros.exceptions.LibroNotFoundException;
import org.example.biblioteca.libros.exceptions.LibroSinStockException;
import org.example.biblioteca.libros.mappers.LibroMapper;
import org.example.biblioteca.libros.models.Libro;
import org.example.biblioteca.libros.repositories.LibrosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * TAREA 5 · Sustituye cada throw por la implementación.
 *   5.0 · Inyecta también el LibrosRepository por el constructor.
 * Si un metodo no te sale, DEJA EL THROW y sigue: el proyecto tiene que compilar.
 */
@Service
public class LibrosServiceImpl implements LibrosService {

    private final AutoresRepository autoresRepository;
    private final LibrosRepository repositorio;
    private final LibroMapper mapper;

    public LibrosServiceImpl(AutoresRepository autoresRepository, LibrosRepository repositorio,
                             LibroMapper mapper) {
        this.autoresRepository = autoresRepository;
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponse> findAll(Optional<String> titulo,
                                       Optional<String> autor,
                                       Optional<BigDecimal> precioMax,
                                       Pageable pageable) {

        Specification<Libro> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));

        if (titulo.isPresent()) {
           spec = spec.and((root, query, cb) ->
                   cb.like(cb.lower(root.get("titulo")), "%" + titulo.get().toLowerCase() + "%"));
        }

        if (autor.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("autor")), "%" + autor.get().toLowerCase() + "%"));
        }

        if (precioMax.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("precio"), "%" + precioMax.get()));
        }

        return repositorio.findAll(spec, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponse findById(Long id) {
        return mapper.toResponse(buscarLibro(id));
    }

    @Override
    @Transactional
    public LibroResponse save(LibroRequest request) {
        if (repositorio.existsByIsbn(request.isbn())) {
            throw new LibroConflictException("Ya existe un libro con el ISBN: " + request.isbn());
        }

        Autor autor = buscarAutor(request.autorId());

        Libro guardado = repositorio.save(mapper.toEntity(request, autor));
        return mapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public LibroResponse update(Long id, LibroRequest request) {

        Libro libro = buscarLibro(id);

        boolean cambiaIsbn = !libro.getIsbn().equals(request.isbn());

        if (cambiaIsbn && repositorio.existsByIsbn(request.isbn())) {
            throw new LibroBadRequestException("Ya existe un libro con el ISBN " + request.isbn());
        }

        Autor autor = buscarAutor(request.autorId());

        mapper.actualizar(libro, request, autor);
        return mapper.toResponse(libro);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Libro libro = buscarLibro(id);
        libro.setDeleted(true);

    }

    @Override
    public LibroResponse ajustarStock(Long id, int cantidad) {

        Libro libro = buscarLibro(id);

        int nuevoStock = libro.getStock() + cantidad;

        if (nuevoStock < 0) {
            throw new LibroSinStockException(libro.getTitulo(), libro.getStock(), cantidad);
        }

        libro.setStock(nuevoStock);
        return mapper.toResponse(libro);

    }

    private Libro buscarLibro(Long id) {
        return repositorio.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new LibroNotFoundException(id));
    }

    private Autor buscarAutor(Long autorId) {
        return autoresRepository.findById(autorId)
                .orElseThrow(() -> new LibroBadRequestException(
                        "El autor con id " + autorId + " no existe"));
    }
}
