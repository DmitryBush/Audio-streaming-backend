package ohio.rizz.streamingservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audio_metadata")
public class SongMetadata {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long contentLength;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private String objectPath;
}
