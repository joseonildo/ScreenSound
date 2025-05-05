package br.com.joseonildo.screensound.repository;

import br.com.joseonildo.screensound.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Artista a WHERE a.nome ILIKE %:nome%")
    List<Artista> buscarArtista(@Param("nome") String nome);
}
