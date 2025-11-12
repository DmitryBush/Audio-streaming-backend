package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByName(String name);
    List<Album> findByArtistId(Long artistId);
    List<Album> findByReleaseDateAfter(LocalDate date);
    List<Album> findByGenreId(Short genreId);
    Page<Album> findAll(Pageable pageable);
}