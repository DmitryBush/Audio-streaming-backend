package ohio.rizz.streamingservice.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_art_url", length = 255)
    private String coverArtUrl;

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