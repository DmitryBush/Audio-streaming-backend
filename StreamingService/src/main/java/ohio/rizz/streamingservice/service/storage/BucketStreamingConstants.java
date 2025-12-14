package ohio.rizz.streamingservice.service.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BucketStreamingConstants {
    AUDIO("audio"), ART("art");

    private final String title;
}
