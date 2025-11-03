package ohio.rizz.streamingservice.service.metadata;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.Tag;

record AudioFileMetadata(AudioHeader header, Tag tag) {
}
