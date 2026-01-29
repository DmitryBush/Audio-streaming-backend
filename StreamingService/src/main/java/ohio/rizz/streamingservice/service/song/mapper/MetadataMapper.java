package ohio.rizz.streamingservice.service.song.mapper;

import ohio.rizz.streamingservice.Entities.SongStreamingMetadata;
import ohio.rizz.streamingservice.dto.song.SongStreamingMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MetadataMapper {
    @Mapping(target = "objectPath", source = "objectStorageLink")
    SongStreamingMetadata mapToAudioMetadata(SongStreamingMetadataDto metadataDto);
    @Mapping(target = "objectStorageLink", source = "objectPath")
    SongStreamingMetadataDto mapToAudioMetadataDto(SongStreamingMetadata metadata);
}
