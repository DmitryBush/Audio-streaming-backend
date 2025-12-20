package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Short> {
    Optional<Genre> findByName(String name);
    boolean existsByName(String name);
    Page<Genre> findAll(Pageable pageable);
}