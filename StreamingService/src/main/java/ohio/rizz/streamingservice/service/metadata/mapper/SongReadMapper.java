package ohio.rizz.streamingservice.service.metadata.mapper;

import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.dto.SongReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongReadMapper {
    SongReadDto mapToSongReadDto(Song song);
}
