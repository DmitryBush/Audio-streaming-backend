package ohio.rizz.streamingservice.service.song.mapper;

import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongReadMapper {
    @Mapping(target = "artistDto", source = "artist")
    @Mapping(target = "albumId", source = "album.id")
    SongReadDto mapToSongReadDto(Song song);
}
