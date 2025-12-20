package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findById(long id);
    Optional<Song> findByName(String name);
    List<Song> findByArtistId(Long artistId);
    List<Song> findByAlbumId(Long albumId);
    List<Song> findByDurationGreaterThan(Integer duration);
    boolean existsByName(String name);
    Page<Song> findAll(Pageable pageable);
}