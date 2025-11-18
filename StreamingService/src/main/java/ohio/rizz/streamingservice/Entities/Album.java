package ohio.rizz.streamingservice.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "albums")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_art_url")
    private String coverArtUrl;

    @Column(name = "disc_count")
    private Short discCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

}