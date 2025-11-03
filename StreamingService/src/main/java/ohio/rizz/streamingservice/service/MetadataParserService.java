package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataParserService {
    private final ContentTypeService contentTypeService;

    public void extractMetadata(MultipartFile multipartFile) {
        AudioFileMetadata metadata = readAudioFileMetadata(multipartFile);

        System.out.println(metadata.header().getTrackLength());
        System.out.println(metadata.tag().getFirstField(FieldKey.ARTIST));
    }

    private AudioFileMetadata readAudioFileMetadata(MultipartFile multipartFile) {
        AudioHeader header;
        Tag tag;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("upload_", contentTypeService.getSuffixType(multipartFile));
            multipartFile.transferTo(tempFile);

            AudioFile audioFile = AudioFileIO.read(tempFile);
            header = audioFile.getAudioHeader();
            tag = audioFile.getTag();
        } catch (ReadOnlyFileException | IOException | CannotReadException | TagException |
                 InvalidAudioFrameException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.ofNullable(tempFile)
                    .ifPresent(File::delete);
        }
        return new AudioFileMetadata(header, tag);
    }
}
