package ohio.rizz.streamingservice.service.song.mapper;

import ohio.rizz.streamingservice.Entities.AudioMetadata;
import ohio.rizz.streamingservice.dto.song.AudioMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MetadataMapper {
    AudioMetadata mapToAudioMetadata(AudioMetadataDto metadataDto);
    AudioMetadataDto mapToAudioMetadataDto(AudioMetadata metadata);
}
