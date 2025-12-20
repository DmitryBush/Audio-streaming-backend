package ohio.rizz.streamingservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long id;

    @Column(name = "name", length = 128, nullable = false, unique = true)
    private String name;

    @Column(name = "track_number_album", columnDefinition = "SMALLINT")
    private Short trackNumberAlbum;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "disc_number")
    private Short discNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_album_id")
    private Album album;
}