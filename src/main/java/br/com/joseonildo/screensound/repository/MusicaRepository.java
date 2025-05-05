package br.com.joseonildo.screensound.repository;

import br.com.joseonildo.screensound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
    Optional<Musica> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Musica a WHERE a.artista.nome ILIKE :nomeArtista")
    List<Musica> buscarMusicasDoArtista(@Param("nomeArtista") String nomeArtista);
}
