package ohio.rizz.streamingservice.service.metadata;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;

/**
 * Composite object received from {@code net.jthink:jaudiotagger}
 */
record AudioFileMetadata(AudioHeader header, Tag tag) {
}
