package br.com.joseonildo.screensound.repository;

import br.com.joseonildo.screensound.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Album a WHERE a.artista.nome ILIKE :nomeArtista AND a.nome ILIKE %:nomeAlbum%")
    Optional<Album> buscarAlbumDoArtista(@Param("nomeArtista") String nomeArtista,
                                         @Param("nomeAlbum") String nomeAlbum);

    @Query("SELECT a FROM Album a WHERE a.artista.nome ILIKE :nomeArtista")
    List<Album> buscarAlbunsDoArtista(@Param("nomeArtista") String nomeArtista);

    @Query("SELECT a FROM Album a WHERE a.artista.nome ILIKE :nomeArtista AND a.nome ILIKE %:nomeAlbum%")
    List<Album> buscarAlbunsParecidos(@Param("nomeArtista") String nomeArtista,
                                         @Param("nomeAlbum") String nomeAlbum);
}
