package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);
    List<Album> findByReleaseDateAfter(LocalDate date);
    List<Album> findByGenreId(Short genreId);
}